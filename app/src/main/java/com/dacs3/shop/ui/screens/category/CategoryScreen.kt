package com.dacs3.shop.ui.screens.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.dacs3.shop.R
import com.dacs3.shop.component.CardLinkItem
import com.dacs3.shop.component.CustomTopBar
import com.dacs3.shop.ui.screens.loading.LoadingScreen
import com.dacs3.shop.ui.screens.login.LoginScreen
import com.dacs3.shop.ui.theme.Black100

@Composable
fun CategoryScreen(navController: NavHostController, categoryViewModel: CategoryViewModel = hiltViewModel()) {
    val uiState by categoryViewModel.categoryUiState.collectAsState()

    LaunchedEffect(true) {
        categoryViewModel.loadCategories()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.background(Color.White)
        ) {
            CustomTopBar(navController)
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = R.string.categories),
                fontSize = 24.sp,
                fontWeight = FontWeight(700),
                color = Black100
            )
            Spacer(modifier = Modifier.height(10.dp))
            if (uiState.isLoading) {
                LoadingScreen()
            } else {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    uiState.categories.forEach {  category ->
                        CardLinkItem(title = category.name!!, image = category.image!!) {
                            println("Category Id: ${category.id}")
                            navController.navigate("category-details/${category.id}")
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}