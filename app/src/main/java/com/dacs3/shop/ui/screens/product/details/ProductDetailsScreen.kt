package com.dacs3.shop.ui.screens.product.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.dacs3.shop.R
import com.dacs3.shop.component.ColorButton
import com.dacs3.shop.component.ColorItemButton
import com.dacs3.shop.component.CommentCard
import com.dacs3.shop.component.CommentInput
import com.dacs3.shop.component.ErrorScreen
import com.dacs3.shop.component.QuantityButton
import com.dacs3.shop.component.SizeButton
import com.dacs3.shop.component.SizeItemButton
import com.dacs3.shop.component.SpacerHeight
import com.dacs3.shop.component.SpacerWidth
import com.dacs3.shop.component.shimmerEffect
import com.dacs3.shop.model.Image
import com.dacs3.shop.model.Product
import com.dacs3.shop.ui.screens.loading.LoadingScreen
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2
import com.dacs3.shop.ui.theme.Primary100
import java.math.BigDecimal
import java.util.Collections.addAll

@Composable
fun ProductDetailsScreen(productId: String?, navController: NavHostController, productDetailsViewModel: ProductDetailsViewModel = hiltViewModel()) {
    val uiState by productDetailsViewModel.productDetailsUiState.collectAsState()

    LaunchedEffect(Unit) {
        productId?.let {
            productDetailsViewModel.loadProduct(productId = productId)
        }
    }

    when {
        uiState.isLoading -> LoadingScreen()
        !uiState.errorMessage.isNullOrEmpty() -> ErrorScreen(message = uiState.errorMessage!!)
        uiState.product != null -> ProductDetailsContent(uiState, productId!!.toInt(), navController, productDetailsViewModel)
    }

}

@Composable
fun ProductDetailsContent(uiState: ProductDetailsUiState, productId: Int, navController: NavHostController, productDetailsViewModel: ProductDetailsViewModel) {
    LaunchedEffect(Unit) {
        productDetailsViewModel.loadComments(productId)
    }
    val comments by productDetailsViewModel.comments.observeAsState(emptyList())
    val users by productDetailsViewModel.users.observeAsState(emptyMap())

    val showSizeModal = remember {
        mutableStateOf(false)
    }
    val showColorModal = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .verticalScroll(rememberScrollState())
            ) {
                TopBarProductDetails(navController = navController, onAddWishlist = {})
                SpacerHeight(int = 15)
                ProductImageContainer(images = uiState.product!!.images)
                SpacerHeight(int = 15)
                Text(
                    text = uiState.product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Black100
                )
                SpacerHeight(int = 10)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (uiState.currentVariant!!.sale!! > 0) {
                        Text(
                            text = "$${productDetailsViewModel.roundDouble(uiState.currentVariant.price!! * (1 - (uiState.currentVariant.sale!! / 100)))}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                            color = Primary100
                        )
                        SpacerWidth(int = 15)
                        Text(
                            text = "$${uiState.currentVariant.price}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            color = Black100,
                            textDecoration = TextDecoration.LineThrough
                        )
                    } else {
                        Text(
                            text = "$${uiState.currentVariant.price}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                            color = Primary100
                        )
                    }
                }
                SpacerHeight(int = 15)
                SizeButton(value = uiState.currentVariant!!.size!!.name) {
                    showSizeModal.value = true
                }
                SpacerHeight(int = 15)
                ColorButton(colorHex = uiState.currentVariant.color!!.value) {
                    showColorModal.value = true
                }
                SpacerHeight(int = 15)
                QuantityButton(quantity = uiState.quantity, onReduce = { productDetailsViewModel.onReduce() }, onIncrease = { productDetailsViewModel.onIncrease() })
                SpacerHeight(int = 30)
                Text(
                    text = "Description",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Black100
                )
                SpacerHeight(int = 20)
                Text(
                    text = uiState.product.description,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(450),
                    color = Black50
                )
                SpacerHeight(int = 20)
                Text(
                    text = "Shipping & Returns",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Black100
                )
                SpacerHeight(int = 20)
                Text(
                    text = "Free standard shipping and free 60-day returns",
                    fontSize = 12.sp,
                    fontWeight = FontWeight(700),
                    color = Black50
                )
                SpacerHeight(int = 30)
                Text(
                    text = "Reviews",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Black100
                )
                SpacerHeight(int = 20)

                if (comments.isEmpty()) {
                    Text(
                        text = "Comment not found",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(450),
                        color = Black50
                    )
                } else {
                    Text(
                        text = "(${comments.size}) Reviews",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(700),
                        color = Black50
                    )
                    SpacerHeight(int = 20)
                    comments.forEach { comment ->
                        val user = users[comment.userId]
                        user?.let {
                            CommentCard(user = user, content = comment.content ?: "")
                            SpacerHeight(int = 15)
                        }
                    }
                }
                Divider()
                SpacerHeight(int = 20)
                CommentInput(value = uiState.comment, onValueChange = { productDetailsViewModel.onCommentChanged(it) }) {
                    productDetailsViewModel.addComment()
                }
                SpacerHeight(int = 80)
            }
        }
        FloatingActionButton(
            onClick = { /* Do something */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(100.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(color = Primary100, shape = RoundedCornerShape(100.dp))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$${uiState.total}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Color.White
                )
                Text(
                    text = "Add to Cart",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(450),
                    color = Color.White
                )
            }
        }
    }
    SizeBottomSheetModal(uiState, showSizeModal, productDetailsViewModel)
    ColorBottomSheetModal(uiState, showColorModal, productDetailsViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeBottomSheetModal(uiState: ProductDetailsUiState, showSizeModal: MutableState<Boolean>, productDetailsViewModel: ProductDetailsViewModel) {
    if (showSizeModal.value) {
        ModalBottomSheet(
            onDismissRequest = { showSizeModal.value = false },
            containerColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(397.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Size",
                            fontSize = 24.sp,
                            fontWeight = FontWeight(700),
                            color = Black100
                        )
                        IconButton(onClick = { showSizeModal.value = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                        }
                    }
                    SpacerHeight(int = 20)
                    LazyColumn {
                        val sizes = uiState.product!!.variants.map { it.size }.toSet().toList()
                        items(sizes) { size ->
                            SizeItemButton(value = size!!.name, isSelected = uiState.currentVariant!!.size == size) {
                                productDetailsViewModel.onSizeChange(sizeId = size.id)
                                showSizeModal.value = false
                            }
                            SpacerHeight(int = 16)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorBottomSheetModal(uiState: ProductDetailsUiState, showColorModal: MutableState<Boolean>, productDetailsViewModel: ProductDetailsViewModel) {
    if (showColorModal.value) {
        val validColors = productDetailsViewModel.getColorsForSize(uiState.currentVariant!!.size!!.id)
        ModalBottomSheet(
            onDismissRequest = { showColorModal.value = false },
            containerColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(397.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Color",
                            fontSize = 24.sp,
                            fontWeight = FontWeight(700),
                            color = Black100
                        )
                        IconButton(onClick = { showColorModal.value = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                        }
                    }
                    SpacerHeight(int = 20)
                    LazyColumn {
                        items(uiState.product!!.variants.map { it.color }
                            .distinctBy { it!!.id }) { color ->
                            val enabled = validColors.contains(color)
                            ColorItemButton(
                                name = color!!.name,
                                colorHex = color.value,
                                isSelected = uiState.currentVariant.color!!.id == color.id,
                                enabled = enabled
                            ) {
                                if (enabled) {
                                    productDetailsViewModel.onColorChange(colorId = color.id)
                                    showColorModal.value = false
                                }
                            }
                            SpacerHeight(int = 16)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarProductDetails(navController: NavHostController, onAddWishlist: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.size(40.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Light2,
                contentColor = Black100
            )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        }
        IconButton(
            onClick = { onAddWishlist() },
            modifier = Modifier.size(40.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Light2,
                contentColor = Black100
            )
        ) {
            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "", modifier = Modifier.size(24.dp))
        }
    }
}

@Composable
fun ProductImageContainer(images: List<Image>) {
    LazyRow {
        items(images) { image ->
            SubcomposeAsyncImage(
                model = image.url,
                contentDescription = null,
                modifier = Modifier
                    .size(width = 161.dp, height = 248.dp),
                contentScale = ContentScale.Crop
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Success) {
                    SubcomposeAsyncImageContent()
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(width = 161.dp, height = 248.dp)
                            .shimmerEffect()
                    )
                }
            }
            SpacerWidth(int = 10)
        }
    }
}