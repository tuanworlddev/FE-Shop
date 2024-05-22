package com.dacs3.shop.ui.screens.product.management

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.dacs3.shop.component.ErrorScreen
import com.dacs3.shop.model.Product
import com.dacs3.shop.ui.screens.loading.LoadingScreen
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Light2
import com.dacs3.shop.ui.theme.Warning

@Composable
fun ProductManageScreen(navHostController: NavHostController, productManageViewModel: ProductManageViewModel = hiltViewModel()) {
    val uiState by productManageViewModel.productManageUiState.collectAsState()

    LaunchedEffect(Unit) {
        productManageViewModel.loadProducts()
    }
    when {
        uiState.isLoading -> LoadingScreen()
        !uiState.errorMessage.isNullOrEmpty() -> ErrorScreen(message = uiState.errorMessage!!)
        else -> ProductManageContent(uiState = uiState, navHostController = navHostController)
    }
}

@Composable
fun ProductManageContent(uiState: ProductManageUiState, navHostController: NavHostController) {
//    Surface(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(horizontal = 10.dp)
//                .verticalScroll(rememberScrollState())
//        ) {
//            Spacer(modifier = Modifier.height(10.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                IconButton(
//                    onClick = { navHostController.popBackStack() },
//                    modifier = Modifier.size(40.dp),
//                    colors = IconButtonDefaults.iconButtonColors(
//                        containerColor = Light2,
//                        contentColor = Black100
//                    )
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowBack,
//                        contentDescription = "",
//                        modifier = Modifier.size(16.dp)
//                    )
//                }
//                Text(
//                    text = "Product Management",
//                    fontWeight = FontWeight(700),
//                    fontSize = 16.sp,
//                    color = Black100
//                )
//                IconButton(
//                    onClick = { navHostController.navigate("create-product") },
//                    modifier = Modifier.size(40.dp),
//                    colors = IconButtonDefaults.iconButtonColors(
//                        containerColor = Light2,
//                        contentColor = Black100
//                    )
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Add,
//                        contentDescription = "create",
//                        modifier = Modifier.size(16.dp)
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(15.dp))
//            uiState.products.forEach { product ->
//                ProductItem(product = product, navHostController)
//                Spacer(modifier = Modifier.height(13.dp))
//            }
//        }
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(product: Product, navHostController: NavHostController) {
//    Card(
//        onClick = { navHostController.navigate("product-details/${product.id}") },
//        colors = CardDefaults.cardColors(
//            containerColor = Light2
//        )
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Start
//        ) {
//            SubcomposeAsyncImage(
//                model = product.image,
//                contentDescription = null,
//                modifier = Modifier.width(80.dp)
//            ) {
//                val state = painter.state
//                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
//                    CircularProgressIndicator(
//                        strokeWidth = 1.dp
//                    )
//                } else {
//                    SubcomposeAsyncImageContent()
//                }
//            }
//
//            Spacer(modifier = Modifier.width(10.dp))
//
//            Column {
//                Text(
//                    text = product.name!!,
//                    color = Black100,
//                    fontWeight = FontWeight(700),
//                    fontSize = 16.sp,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//                Text(
//                    text = "Price: $${product.price}",
//                    color = Black100,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight(450)
//                )
//                Text(
//                    text = "Sale: ${product.sale}%",
//                    color = Black100,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight(450)
//                )
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    IconButton(
//                        onClick = {  },
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(6.dp))
//                            .border(width = 1.dp, color = Warning, shape = RoundedCornerShape(6.dp))
//                            .size(30.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Edit,
//                            contentDescription = "edit",
//                            tint = Warning,
//                            modifier = Modifier.size(16.dp)
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(5.dp))
//                    IconButton(
//                        onClick = {
//                        },
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(6.dp))
//                            .border(
//                                width = 1.dp,
//                                color = Color.Red,
//                                shape = RoundedCornerShape(6.dp)
//                            )
//                            .size(30.dp)
//
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Delete,
//                            contentDescription = "delete",
//                            tint = Color.Red,
//                            modifier = Modifier.size(16.dp)
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(5.dp))
//                }
//            }
//        }
//    }
}