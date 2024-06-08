package com.dacs3.shop.ui.screens.category.update

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dacs3.shop.component.ButtonPrimary
import com.dacs3.shop.component.ErrorScreen
import com.dacs3.shop.component.SpacerHeight
import com.dacs3.shop.component.SpacerWidth
import com.dacs3.shop.component.TopAppBarComponent
import com.dacs3.shop.model.Category
import com.dacs3.shop.ui.screens.loading.LoadingDialog
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2
import com.dacs3.shop.ui.theme.Warning

@Composable
fun CategoryUpdateScreen(categoryId: String?, navController: NavHostController, viewModel: CategoryUpdateViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(Unit) {
        categoryId?.let {
            viewModel.loadCategory(it.toInt())
        }
    }

    if (uiState.isLoading) {
        LoadingDialog()
    }

    if (uiState.error != null) {
        ErrorScreen(message = uiState.error!!)
    } else {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                SpacerHeight(int = 10)
                TopAppBarComponent(navController = navController, title = "Update")
                SpacerHeight(int = 15)

                if (uiState.category != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp))
                            .border(width = 1.dp, color = Black100, shape = RoundedCornerShape(8.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(painter = rememberAsyncImagePainter(model = uiState.category?.image!!), contentDescription = null, modifier = Modifier
                            .size(120.dp)
                            .clip(
                                CircleShape
                            )
                        )
                    }
                    SpacerHeight(int = 10)
                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Upload Image")
                    }

                    SpacerHeight(int = 15)
                    OutlinedTextField(
                        value = uiState.name,
                        onValueChange = { viewModel.onNameChanged(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Name") }
                    )

                    SpacerHeight(int = 20)

                    ButtonPrimary(onClick = { /*TODO*/ }, text = "Update", enabled = uiState.isActive, modifier = Modifier.align(Alignment.CenterHorizontally))
                    SpacerHeight(int = 20)
                }
            }
        }
    }
}
