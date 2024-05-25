package com.dacs3.shop.ui.screens.product.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dacs3.shop.R
import com.dacs3.shop.component.CustomTextField
import com.dacs3.shop.component.MutableTextFiled
import com.dacs3.shop.component.RichText
import com.dacs3.shop.component.SpacerHeight
import com.dacs3.shop.component.TopAppBarComponent
import com.dacs3.shop.ui.theme.Black50

@Composable
fun CreateProductScreen(
    navController: NavHostController,
    viewModel: CreateProductViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
        ) {
            SpacerHeight(int = 15)
            TopAppBarComponent(navController = navController, title = "Create Product")
            SpacerHeight(int = 20)
            CustomTextField(
                value = uiState.name,
                onValueChange = { viewModel.onNameChanged(it) },
                label = "Product Name",
                errorMessage = null
            )
            SpacerHeight(int = 15)
            MutableTextFiled(
                value = uiState.description,
                onValueChange = { viewModel.onDescriptionChanged(it) },
                label = "Description"
            )
            SpacerHeight(int = 15)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(89.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .border(width = 1.dp, color = Black50, shape = RoundedCornerShape(8.dp))
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.product), contentDescription = null)
                Image(painter = painterResource(id = R.drawable.product), contentDescription = null)
                Image(painter = painterResource(id = R.drawable.product), contentDescription = null)
            }
            SpacerHeight(int = 15)
        }
    }
}

