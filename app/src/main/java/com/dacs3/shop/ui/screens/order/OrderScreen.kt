package com.dacs3.shop.ui.screens.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dacs3.shop.R
import com.dacs3.shop.component.ButtonPrimary
import com.dacs3.shop.component.SpacerHeight
import com.dacs3.shop.component.SpacerWidth
import com.dacs3.shop.model.OrderDetails
import com.dacs3.shop.ui.screens.notification.NoNotificationScreen
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2
import com.dacs3.shop.ui.theme.Primary100
import com.dacs3.shop.ui.theme.circularFont
import com.dacs3.shop.ui.theme.white

@Composable
fun OrderScreen(navController: NavHostController, viewModel: OrderViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadOrders()
    }
    if (uiState.orders.isEmpty()) {
        NoOrderScreen(onCategoryScreenChange = {
            navController.navigate("category")
        })
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
            ) {
                SpacerHeight(int = 10)
                Text(
                    text = stringResource(id = R.string.orders),
                    fontSize =  16.sp,
                    fontWeight = FontWeight(700),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Black100, fontFamily = circularFont
                )
                SpacerHeight(int = 15)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.listStatus.forEach {
                        Button(
                            onClick = { viewModel.onStatusChange(it) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (uiState.selectedStatus == it) Primary100 else Light2,
                                contentColor = if (uiState.selectedStatus == it) Color.White else Black100
                            ),
                            modifier = Modifier.height(27.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            Text(text = it.name, fontSize = 12.sp, fontWeight = FontWeight(450))
                        }
                    }
                }
                SpacerHeight(int = 12)
                if (uiState.orderFilter.isEmpty()) {
                    Text(
                        text = "No Orders yet",
                        fontSize = 16.sp,
                        color = Black50,
                        fontFamily = circularFont,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    uiState.orderFilter.forEach {
                        OrderCard(orderDetails = it, onClick = {
                            navController.navigate("order-details/${it.id}")
                        } )
                        SpacerHeight(int = 10)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderCard(orderDetails: OrderDetails, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(color = Light2, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_order), contentDescription = null, tint = Black100, modifier = Modifier.size(24.dp))
            SpacerWidth(int = 8)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Order #${orderDetails.id}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = Black100
                )
                Text(
                    text = "${orderDetails.items?.size} item",
                    fontSize = 12.sp,
                    fontWeight = FontWeight(450),
                    color = Black50
                )
            }
            Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "", tint = Black100, modifier = Modifier.size(24.dp))
        }
    }
}

@Composable
fun NoOrderScreen(onCategoryScreenChange: () -> Unit) {
    Surface(
        color = white,
        modifier = Modifier.fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.orders),
                fontSize =  16.sp,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Black100, fontFamily = circularFont
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(id = R.drawable.checkout), contentDescription = "order", modifier = Modifier.size(100.dp))
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(text = "No Orders yet", fontSize = 24.sp, color = Black100, fontFamily = circularFont)
                    Spacer(modifier = Modifier.height(15.dp))
                    ButtonPrimary(onClick = { onCategoryScreenChange() }, text = "Explore Categories")
                }
            }
        }
    }
}

