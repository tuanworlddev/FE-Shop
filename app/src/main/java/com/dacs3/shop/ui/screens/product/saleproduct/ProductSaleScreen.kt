package com.dacs3.shop.ui.screens.product.saleproduct

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dacs3.shop.ui.screens.home.HomeNewIn
import com.dacs3.shop.ui.screens.loading.LoadingScreen
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2

@Composable
fun ProductSaleScreen(navController: NavHostController, productSaleViewModel: ProductSaleViewModel = hiltViewModel()) {
    val uiState by productSaleViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        productSaleViewModel.loadProduct()
    }

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
                horizontalArrangement = Arrangement.Start
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
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Top Selling",
                fontSize = 16.sp,
                fontWeight = FontWeight(700),
                color = Black100
            )

            Spacer(modifier = Modifier.height(15.dp))

            if (uiState.isLoading) {
                LoadingScreen()
            } else {
                if (uiState.products.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Product Not Found", color = Black50)
                    }
                } else {
                    HomeNewIn(products = uiState.products, navController = navController)
                }
            }
        }
    }
}