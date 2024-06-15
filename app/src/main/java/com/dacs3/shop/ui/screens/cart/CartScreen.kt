package com.dacs3.shop.ui.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        var selectedAll by remember {
                            mutableStateOf(false)
                        }
                        Checkbox(checked = selectedAll, onCheckedChange = {
                            selectedAll = it
                            if (selectedAll) {
                                viewModel.selectAllItems()
                            } else {
                                viewModel.deselectAllItems()
                            } }
                        )
                        SpacerWidth(int = 5)
                        Text(text = "Select All (${uiState.selectedItems.size})")
                    }
                    Divider()
                    uiState.carts.forEach { cart ->
                        CartItemComponent(
                            cart = cart,
                            onSelect = { cartId ->
                                viewModel.toggleSelectItem(cartId)
                            },
                            uiState = uiState,
                            viewModel = viewModel
                        )
                        SpacerHeight(int = 10)
                    }
                }
            }
        }
        if (uiState.carts.isNotEmpty()) {
            FloatingActionButton(
                onClick = {
                },
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
                        text = "Total Price",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(450),
                        color = Color.White, fontFamily = circularFont
                    )
                    Text(
                        text = "$${uiState.totalPrice}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Color.White, fontFamily = circularFont
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemComponent(cart: Cart, onSelect: (cartId: Int) -> Unit, uiState: CartUiState, viewModel: CartViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = uiState.selectedItems.contains(cart.id),
                onCheckedChange = {
                    onSelect(cart.id!!)
                }
            )
            SpacerWidth(int = 5)
            Text(text = "Select")
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Light2
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                SubcomposeAsyncImage(
                    model = cart.productImage,
                    contentDescription = null,
                    modifier = Modifier.width(80.dp)
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        Box(modifier = Modifier
                            .size(width = 60.dp, height = 80.dp)
                            .shimmerEffect())
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }

                SpacerWidth(int = 10)

                Column {
                    Text(
                        text = cart.productImage!!,
                        color = Black100,
                        fontWeight = FontWeight(700),
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Price: $${cart.variant?.price!!}",
                        color = Black100,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(450)
                    )
                    Text(
                        text = "Sale: ${cart.variant.sale!!}%",
                        color = Black100,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(450)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Quantity:",
                            color = Black100,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450)
                        )
                        SpacerWidth(int = 12)
                        IconButton(onClick = { viewModel.onReduce(cart.id!!) }, modifier = Modifier.size(24.dp)) {
                            Icon(painter = painterResource(id = R.drawable.chevron_left_24), contentDescription = null, modifier = Modifier.fillMaxSize())
                        }
                        SpacerWidth(int = 10)
                        Text(text = cart.quantity!!.toString(), color = Black100, fontWeight = FontWeight.Bold)
                        SpacerWidth(int = 10)
                        IconButton(onClick = { viewModel.onIncrease(cart.id!!) }, modifier = Modifier.size(24.dp)) {
                            Icon(painter = painterResource(id = R.drawable.chevron_right_24), contentDescription = null, modifier = Modifier.fillMaxSize())
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {
                                      viewModel.onDeleteCart(cart.id!!)
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color.Red,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .size(30.dp)

                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete",
                                tint = Color.Red,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}