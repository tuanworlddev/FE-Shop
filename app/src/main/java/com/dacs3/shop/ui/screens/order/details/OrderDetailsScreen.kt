package com.dacs3.shop.ui.screens.order.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.dacs3.shop.R
import com.dacs3.shop.component.SpacerHeight
import com.dacs3.shop.component.SpacerWidth
import com.dacs3.shop.component.TopAppBarComponent
import com.dacs3.shop.component.shimmerEffect
import com.dacs3.shop.model.Cart
import com.dacs3.shop.model.OrderItem
import com.dacs3.shop.model.OrderStatus
import com.dacs3.shop.ui.screens.cart.CartViewModel
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2
import com.dacs3.shop.ui.theme.Primary100
import com.dacs3.shop.ui.theme.Primary50

@Composable
fun OrderDetailsScreen(
    orderId: String?,
    navController: NavHostController,
    viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        orderId?.let {
            viewModel.getOrderDetails(it.toInt())
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(horizontal = 10.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                SpacerHeight(int = 10)
                TopAppBarComponent(navController = navController, title = "Order #${orderId}")
                SpacerHeight(int = 15)
                OrderStatusItem(isCompleted = uiState.orderDetails?.status == OrderStatus.Pending, status = OrderStatus.Pending.name)
                SpacerHeight(int = 20)
                OrderStatusItem(isCompleted = uiState.orderDetails?.status == OrderStatus.Confirmed, status = OrderStatus.Confirmed.name)
                SpacerHeight(int = 20)
                OrderStatusItem(isCompleted = uiState.orderDetails?.status == OrderStatus.Shipped, status = OrderStatus.Shipped.name)
                SpacerHeight(int = 20)
                OrderStatusItem(isCompleted = uiState.orderDetails?.status == OrderStatus.Delivered, status = OrderStatus.Delivered.name)
                SpacerHeight(int = 20)
                Text(text = "Order Items", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Black100)
                SpacerHeight(int = 15)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Light2, shape = RoundedCornerShape(8.dp))
                        .clip(shape = RoundedCornerShape(8.dp))
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(72.dp)
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row {
                                Icon(painter = painterResource(id = R.drawable.ic_order), contentDescription = null, modifier = Modifier.size(24.dp), tint = Black100)
                                SpacerWidth(int = 16)
                                Text(text = "${uiState.orderDetails?.items?.size} item")
                            }
                            TextButton(
                                onClick = { viewModel.onViewAllClick() },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = Primary100,
                                    containerColor = Light2
                                )
                            ) {
                                Text(text = "View All")
                            }
                        }
                        if(uiState.isViewAll) {
                            uiState.orderDetails?.items?.forEach { orderItem ->
                                OrderItemComponent(orderItem = orderItem)
                                SpacerHeight(int = 10)
                            }
                        }
                    }
                }
                SpacerHeight(int = 25)
                Text(text = "Shipping details", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Black100)
                SpacerHeight(int = 15)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .background(color = Light2, shape = RoundedCornerShape(8.dp))
                        .clip(shape = RoundedCornerShape(8.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "${uiState.orderDetails?.address?.addressLine}, ${uiState.orderDetails?.address?.commune}, ${uiState.orderDetails?.address?.district}, ${uiState.orderDetails?.address?.province}, ${uiState.orderDetails?.address?.country}",
                            fontWeight = FontWeight(450),
                            fontSize = 12.sp,
                            color = Black100,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(text = "${uiState.orderDetails?.address?.phoneNumber}",
                            fontWeight = FontWeight(450),
                            fontSize = 12.sp,
                            color = Black100
                        )
                    }
                }
                if (uiState.orderDetails?.status == OrderStatus.Pending) {
                    SpacerHeight(int = 25)
                    Button(
                        onClick = { viewModel.updateOrderStatus(OrderStatus.Cancelled) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.danger_btn),
                            contentColor = Color.White
                        ),
                        border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.danger_btn)),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(text = "Cancel Order")
                    }
                    if (uiState.isCancelled) {
                        navController.navigate("order")
                    }
                    SpacerHeight(int = 15)
                } else if (uiState.orderDetails?.status == OrderStatus.Shipped) {
                    SpacerHeight(int = 25)
                    Button(
                        onClick = { viewModel.updateOrderStatus(OrderStatus.Delivered) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.success_btn),
                            contentColor = Color.White
                        ),
                        border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.success_btn)),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(text = "Delivered Order")
                    }
                    SpacerHeight(int = 15)
                }
            }
        }
    }
}

@Composable
fun OrderStatusItem(isCompleted: Boolean, status: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = if (isCompleted) Primary100 else Primary50, shape = CircleShape)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(8.dp))
        }
        SpacerWidth(int = 16)
        Text(
            text = status,
            color = if (isCompleted) Black100 else Black50,
            fontWeight = FontWeight(450),
            fontSize = 16.sp
        )
    }
}

@Composable
fun OrderItemComponent(orderItem: OrderItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
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
                model = orderItem.productImage,
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
                        text = orderItem.productName!!,
                        color = Black100,
                        fontWeight = FontWeight(450),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(200.dp)
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
                            text = "- ${orderItem.variant?.size!!.name}",
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
                            text = "- ${orderItem.variant.color!!.name}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(450),
                            color = Black100
                        )
                    }
                    Text(
                        text = "Quantity: ${orderItem.quantity}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(450),
                        color = Black100
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total: $${orderItem.variant?.price!! * orderItem.quantity!! * (1 - orderItem.variant.sale!! / 100)}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(450),
                        color = Black100
                    )
                }
            }
        }
    }
}