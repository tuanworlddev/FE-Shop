package com.dacs3.shop.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dacs3.shop.R
import com.dacs3.shop.component.ProductCard
import com.dacs3.shop.model.Category
import com.dacs3.shop.model.Product
import com.dacs3.shop.ui.screens.loading.LoadingScreen
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2
import com.dacs3.shop.ui.theme.Primary100

@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel = hiltViewModel()) {
    val uiState by homeViewModel.homeUiState.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.loadData()
        homeViewModel.loadUser()
    }

    if (uiState.isLoading) {
        LoadingScreen()
    } else {
        HomeContent(uiState = uiState, navController = navController)
    }
}

@Composable
fun HomeContent(uiState: HomeUiState, navController: NavHostController) {
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
            SearchBarButton()
            Section(
                title = stringResource(id = R.string.categories),
                onSeeAllClick = { navController.navigate("categories") }
            ) {
                HomeCategory(uiState.categories, navController)
            }
            Section(
                title = stringResource(id = R.string.top_selling),
                onSeeAllClick = { navController.navigate("product-sale") }
            ) {
                HomeTopSelling(uiState.saleProducts, navController)
            }
            Section(
                title = stringResource(id = R.string.new_in),
                onSeeAllClick = { navController.navigate("product-new") }
            ) {
                HomeNewIn(uiState.newProducts, navController)
            }
        }
    }
}

@Composable
fun Section(title: String, onSeeAllClick: () -> Unit, content: @Composable () -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = Black100,
                fontSize = 16.sp,
                fontWeight = FontWeight(700)
            )
            TextButton(onClick = onSeeAllClick) {
                Text(text = stringResource(id = R.string.see_all))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        content()
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun SearchBarButton() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .background(color = Light2, shape = RoundedCornerShape(100.dp))
                .height(40.dp)
                .clip(RoundedCornerShape(100.dp))
                .fillMaxWidth(0.8f)
                .padding(10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = stringResource(id = R.string.search), color = Black100)
            }
        }
        IconButton(
            onClick = { /*TODO*/ },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Primary100,
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun HomeCategory(categories: List<Category>?, navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            if (categories.isNullOrEmpty()) {
                Text(text = "Category Not Found", color = Black50, textAlign = TextAlign.Center)
            } else {
                categories.forEach { category ->
                    HomeCategoryCard(category, navController)
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}

@Composable
fun HomeTopSelling(saleProducts: List<Product>?, navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (saleProducts.isNullOrEmpty()) {
            Text(text = "Sale Products Not Found", color = Black50, textAlign = TextAlign.Center)
        } else {
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                saleProducts.forEach { product ->
                    ProductCard(product, navController)
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
        }
    }
}

@Composable
fun HomeNewIn(products: List<Product>?, navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (products.isNullOrEmpty()) {
            Text(text = "New Products Not Found", color = Black50, textAlign = TextAlign.Center)
        } else {
            val productList = products.chunked(2)
            productList.forEach { product2 ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    product2.forEach { product ->
                        ProductCard(product = product, navHostController = navController)
                    }
                    if (product2.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun HomeCategoryCard(category: Category, navController: NavHostController) {
    Column(
        modifier = Modifier
            .width(65.dp)
            .clickable {
                navController.navigate("category-details/${category.id}")
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = category.image),
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Light2)
        )
        Text(
            text = category.name!!,
            fontSize = 12.sp,
            fontWeight = FontWeight(450),
            color = Black100,
            minLines = 2,
            maxLines = 2,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp,
            softWrap = true,
            overflow = TextOverflow.Ellipsis
        )
    }
}
