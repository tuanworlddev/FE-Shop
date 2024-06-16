package com.dacs3.shop.ui.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dacs3.shop.component.SpacerHeight
import com.dacs3.shop.component.SpacerWidth
import com.dacs3.shop.component.TopAppBarComponent
import com.dacs3.shop.model.Cart
import com.dacs3.shop.ui.screens.loading.LoadingScreen
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.dacs3.shop.R
import com.dacs3.shop.component.ButtonPrimary
import com.dacs3.shop.component.shimmerEffect
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Primary100
import com.dacs3.shop.ui.theme.circularFont

@Composable
fun CartScreen(navController: NavHostController, viewModel: CartViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.loadCart()
    }

    if (!viewModel.isUserExists()) {
        navController.navigate("login")
    }
    
    when {
        uiState.isLoading -> LoadingScreen()
        else -> CartScreenContent(uiState, viewModel, navController)
    }
}

@Composable
fun CartScreenContent(uiState: CartUiState, viewModel: CartViewModel, navController: NavHostController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
            ) {
                SpacerHeight(int = 10)
                TopAppBarComponent(navController = navController, title = "Carts")
                SpacerHeight(int = 15)
                if (uiState.carts.isEmpty()) {
                    Text(
                        text = "No cart",
                        color = Black50,
                        fontWeight = FontWeight(450),
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "Remove All",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(450),
                        color = Black100,
                        modifier = Modifier.align(Alignment.End)
                            .clickable { viewModel.deleteAllCart() }
                    )
                    SpacerHeight(int = 10)
                    uiState.carts.forEach { cart ->
                        CartItemComponent(cart, viewModel)
                        SpacerHeight(int = 10)
                    }
                    SpacerHeight(int = 15)
                    Divider()
                    SpacerHeight(int = 15)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Subtotal",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black50
                        )
                        Text(
                            text = "$${uiState.carts.sumOf { it.quantity!! * (1 - it.variant?.sale!! / 100) * it.variant.price!! }}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black100
                        )
                    }
                    SpacerHeight(int = 8)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Shipping Cost",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black50
                        )
                        Text(
                            text = "$${uiState.shippingCost}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black100
                        )
                    }
                    SpacerHeight(int = 8)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Tax",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black50
                        )
                        Text(
                            text = "$${uiState.tax}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black100
                        )
                    }
                    SpacerHeight(int = 8)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black50
                        )
                        Text(
                            text = "$${uiState.carts.sumOf { it.quantity!! * (1 - it.variant?.sale!! / 100) * it.variant.price!! } + uiState.tax + uiState.shippingCost}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black100
                        )
                    }
                    SpacerHeight(int = 20)
                    ButtonPrimary(onClick = { navController.navigate("checkout") }, text = "Checkout", modifier = Modifier.fillMaxWidth())
                    SpacerHeight(int = 15)
                }
            }
        }
    }
}

@Composable
fun CartItemComponent(cart: Cart, viewModel: CartViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Light2, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Light2
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(Light2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                model = cart.productImage,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .background(color = Light2, shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop)
            {
                val state = painter.state
                if (state is AsyncImagePainter.State.Success) {
                    SubcomposeAsyncImageContent()
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(64.dp)
                            .shimmerEffect()
                    )
                }
            }
            SpacerWidth(int = 8)
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = cart.productName!!,
                        color = Black100,
                        fontWeight = FontWeight(450),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(200.dp)
                    )
                    Text(
                        text = "$${viewModel.roundDouble(cart.variant?.price!! * cart.quantity!! * (1 - cart.variant.sale!! / 100))}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black100
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Size ",
                            color = Black50,
                            fontWeight = FontWeight(450),
                            fontSize = 12.sp,
                        )
                        Text(
                            text = "- ${cart.variant?.size!!.name}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(450),
                            color = Black100
                        )
                        SpacerWidth(int = 8)
                        Text(
                            text = "Color ",
                            color = Black50,
                            fontWeight = FontWeight(450),
                            fontSize = 12.sp,
                        )
                        Text(
                            text = "- ${cart.variant.color!!.name}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(450),
                            color = Black100
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = { viewModel.reduceQuantity(cart.id!!) },
                            modifier = Modifier.size(24.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Primary100,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(painter = painterResource(id = R.drawable.minus), contentDescription = null, modifier = Modifier.size(12.dp))
                        }
                        Text(text = cart.quantity.toString())
                        IconButton(
                            onClick = { viewModel.increaseQuantity(cart.id!!) },
                            modifier = Modifier.size(24.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Primary100,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(painter = painterResource(id = R.drawable.addicon), contentDescription = null, modifier = Modifier.size(12.dp))
                        }
                    }
                }
            }
        }
    }
}