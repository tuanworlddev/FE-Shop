package com.dacs3.shop.ui.screens.order.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dacs3.shop.R
import com.dacs3.shop.component.SpacerHeight
import com.dacs3.shop.ui.screens.order.OrderCard
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2
import com.dacs3.shop.ui.theme.Primary100
import com.dacs3.shop.ui.theme.circularFont

@Composable
fun OrderMangerScreen(navController: NavHostController, viewModel: OrderManageViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAllOrders()
    }

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
                        navController.navigate("order-item/${it.id}")
                    } )
                    SpacerHeight(int = 10)
                }
            }
        }
    }
}