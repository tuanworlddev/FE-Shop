package com.dacs3.shop.ui.screens.product.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.dacs3.shop.R
import com.dacs3.shop.component.ErrorScreen
import com.dacs3.shop.model.Product
import com.dacs3.shop.ui.screens.loading.LoadingScreen
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2
import com.dacs3.shop.ui.theme.Primary100
import java.math.BigDecimal

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
        uiState.product != null -> ProductDetailsContent(product = uiState.product!!, navController)
    }

}

@Composable
fun ProductDetailsContent(product: Product, navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(10.dp))
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
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", modifier = Modifier.size(16.dp))
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(40.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Light2,
                        contentColor = Black100
                    )
                ) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "", modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            val price = product.price ?: BigDecimal.ZERO
            val sale: Float = product.sale ?: 0f

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                SubcomposeAsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        CircularProgressIndicator(
                            strokeWidth = 1.dp
                        )
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = if (sale > 0) painterResource(id = R.drawable.product_sale) else painterResource(id = R.drawable.product_new),
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = product.name!!,
                fontSize = 16.sp,
                fontWeight = FontWeight(700),
                color = Black100
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (sale > 0f) {
                    Text(
                        text = "$${"%.2f".format(price.toFloat() * (1 - sale / 100))}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Primary100
                    )
                    Text(
                        text = "$${price}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Primary100,
                        textDecoration = TextDecoration.LineThrough
                    )
                } else {
                    Text(
                        text = "$${price}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Primary100
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Light2,
                        contentColor = Black100
                    )
                ) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "add", modifier = Modifier.size(16.dp))
                    Text(
                        text = "Add to Cart",
                        fontSize = 14.sp
                    )
                }
                Button(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "buy", modifier = Modifier.size(16.dp))
                    Text(
                        text = "Buy Now",
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            product.attributes?.let { attributes ->
                Text(
                    text = "Attributes",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Black100
                )

                Spacer(modifier = Modifier.height(10.dp))

                attributes.forEach { attribute ->
                    Text(
                        text = "${attribute.type}: ${attribute.value}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(450),
                        color = Black50
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            product.description?.let { description ->
                Text(
                    text = "Description",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Black100
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(450),
                    color = Black50
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

