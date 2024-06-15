package com.dacs3.shop.ui.screens.category.update

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dacs3.shop.component.AlertSuccess
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
import com.dacs3.shop.ui.theme.Primary100
import com.dacs3.shop.ui.theme.Warning
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnrememberedMutableState")
@Composable
fun CategoryUpdateScreen(categoryId: String?, navController: NavHostController, viewModel: CategoryUpdateViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        categoryId?.let {
            viewModel.loadCategory(it.toInt())
        }
    }

    val imagePicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            scope.launch {
                viewModel.onImageUriChanged(it)
                val imageBitmap = withContext(Dispatchers.IO) {
                    context.contentResolver.openInputStream(it)?.use {
                        BitmapFactory.decodeStream(it)?.asImageBitmap()
                    }
                }
                imageBitmap?.let {
                    viewModel.onImageBitmapChanged(it)
                }
            }
        }
    }

    if (uiState.isLoading) {
        LoadingDialog(text = "Updating")
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
                    if (!uiState.successMessage.isNullOrEmpty()) {
                        AlertSuccess(text = uiState.successMessage!!)
                        SpacerHeight(int = 15)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = Black100,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (uiState.imageBitmap != null) {
                            Image(
                                bitmap = uiState.imageBitmap!!, contentDescription = null,
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                                    .background(color = Light2, shape = CircleShape)
                                    .border(width = 2.dp, color = Primary100, shape = CircleShape)
                            )
                        } else {
                            Image(
                                painter = rememberAsyncImagePainter(model = uiState.category?.image!!),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                                    .background(color = Light2, shape = CircleShape)
                                    .border(width = 2.dp, color = Primary100, shape = CircleShape)
                            )
                        }
                    }
                    SpacerHeight(int = 10)
                    OutlinedButton(
                        onClick = {
                                  imagePicker.launch("image/*")
                        },
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

                    ButtonPrimary(onClick = { viewModel.onUpdateCategory() }, text = "Update", enabled = uiState.isActive, modifier = Modifier.align(Alignment.CenterHorizontally))
                    SpacerHeight(int = 20)
                }
            }
        }
    }
}
