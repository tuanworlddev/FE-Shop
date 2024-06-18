package com.dacs3.shop.ui.screens.product.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.component.ErrorScreen
import com.dacs3.shop.model.CartRequest
import com.dacs3.shop.model.Color
import com.dacs3.shop.model.Comment
import com.dacs3.shop.model.User
import com.dacs3.shop.repository.AuthRepository
import com.dacs3.shop.repository.CartRepository
import com.dacs3.shop.repository.CommentRepository
import com.dacs3.shop.repository.ProductRepository
import com.dacs3.shop.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val commentRepository: CommentRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository
) : ViewModel() {
    private val _productDetailsUiState = MutableStateFlow(ProductDetailsUiState())
    val productDetailsUiState = _productDetailsUiState

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> = _comments

    private val _users = MutableLiveData<Map<Int, User>>()
    val users: LiveData<Map<Int, User>> = _users

    fun loadComments(productId: Int) {
        viewModelScope.launch {
            if (authRepository.user != null) {
                val commentsList = commentRepository.getComments(productId)
                _comments.value = commentsList
                loadUsers(commentsList)
            }
        }
    }

    private suspend fun loadUsers(comments: List<Comment>) {
        val usersMap = mutableMapOf<Int, User>()
        comments.forEach { comment ->
            comment.userId?.let { userId ->
                val response = userRepository.getUserById(userId)
                if (response.isSuccessful) {
                    response.body()?.let { user ->
                        usersMap[userId] = user
                    }
                }
            }
        }
        _users.value = usersMap
    }

    fun addComment() {
        val currentUiState = _productDetailsUiState.value
        viewModelScope.launch {
            if (authRepository.user != null && currentUiState.comment.isNotEmpty()) {
                commentRepository.addComment(Comment(userId = authRepository.user?.id!!, productId = currentUiState.product?.id!!, content = currentUiState.comment))
                _productDetailsUiState.value = _productDetailsUiState.value.copy(comment = "")
                loadComments(_productDetailsUiState.value.product?.id!!)
            }
        }
    }

    fun onCommentChanged(content: String) {
        _productDetailsUiState.value = _productDetailsUiState.value.copy(comment = content)
    }

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            _productDetailsUiState.value = _productDetailsUiState.value.copy(errorMessage = null, isLoading = true)
            try {
                val id = productId.toInt()
                val response = productRepository.getProductById(id)
                if (response.isSuccessful) {
                    val result = response.body()
                    val firstVariant = result!!.variants.first()
                    _productDetailsUiState.value = _productDetailsUiState.value.copy(product = result, currentVariant = firstVariant, total = priceSale(firstVariant.price!!, firstVariant.sale!!, 1), isLoading = false)
                } else {
                    _productDetailsUiState.value = _productDetailsUiState.value.copy(errorMessage = response.errorBody().toString(), isLoading = false)
                }
            } catch (e: Exception) {
                _productDetailsUiState.value = _productDetailsUiState.value.copy(errorMessage = e.message, isLoading = false)
            }
        }
    }

    fun onSizeChange(sizeId: Int) {
        val currentVariants = _productDetailsUiState.value.product!!.variants
        val newVariant = currentVariants.find { it.size!!.id == sizeId}
        if (newVariant != null) {
            _productDetailsUiState.value = _productDetailsUiState.value.copy(currentVariant = newVariant, total = priceSale(newVariant.price!!, newVariant.sale!!, 1), quantity = 1)
        }
    }

    fun onColorChange(colorId: Int) {
        val currentVariants = _productDetailsUiState.value.product!!.variants
        val newVariant = currentVariants.find { it.size == _productDetailsUiState.value.currentVariant!!.size && it.color!!.id == colorId }
        if (newVariant != null) {
            _productDetailsUiState.value = _productDetailsUiState.value.copy(currentVariant = newVariant, total = priceSale(newVariant.price!!, newVariant.sale!!, 1), quantity = 1)
        }
    }

    fun getColorsForSize(sizeId: Int): List<Color?> {
        return _productDetailsUiState.value.product?.variants
            ?.filter { it.size!!.id == sizeId }
            ?.map { it.color }
            ?.distinctBy { it!!.id }
            ?: emptyList()
    }

    fun onReduce() {
        val currentQuantity = _productDetailsUiState.value.quantity
        val currentVariant = _productDetailsUiState.value.currentVariant
        if (currentQuantity > 1) {
            val newQuantity = currentQuantity - 1
            _productDetailsUiState.value = _productDetailsUiState.value.copy(quantity = newQuantity, total = priceSale(currentVariant!!.price!!, currentVariant.sale!!, newQuantity))
        }
    }

    fun onIncrease() {
        val currentQuantity = _productDetailsUiState.value.quantity
        val currentVariant = _productDetailsUiState.value.currentVariant
        if (currentQuantity < currentVariant!!.quantity!!) {
            val newQuantity = currentQuantity + 1
            _productDetailsUiState.value = _productDetailsUiState.value.copy(quantity = newQuantity, total = priceSale(currentVariant.price!!, currentVariant.sale!!, newQuantity))
        }
    }

    private fun priceSale(price: Double, sale: Double, quantity: Int): Double {
        return roundDouble(price * (1 - (sale / 100)) * quantity)
    }

    fun roundDouble(value: Double): Double {
        return BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }

    fun onAddToCart() {
        viewModelScope.launch {
            try {
                val currentUiState = _productDetailsUiState.value
                val currentVariant = currentUiState.currentVariant
                if (currentVariant != null) {
                    cartRepository.getCarts().apply {
                        val carts = this.body()
                        val existingCart = carts?.find { it.variant?.id == currentVariant.id }

                        if (existingCart != null) {
                            _productDetailsUiState.value = currentUiState.copy(isExists = true)
                        } else {
                            val cartRequest = CartRequest(variantId = currentVariant.id!!, quantity = currentUiState.quantity)
                            val response = cartRepository.addCart(cartRequest)
                            if (response.isSuccessful) {
                                _productDetailsUiState.value = currentUiState.copy(isAddedToCart = true)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _productDetailsUiState.value = _productDetailsUiState.value.copy(errorMessage = "Error: ${e.message}")
            }
        }
    }

    fun isUserExists(): Boolean {
        return authRepository.user != null
    }

    fun onChangeIsAddedToCart(state: Boolean) {
        _productDetailsUiState.value = _productDetailsUiState.value.copy(isAddedToCart = state)
    }

    fun onChangeExists(state: Boolean) {
        _productDetailsUiState.value = _productDetailsUiState.value.copy(isExists = state)
    }
}