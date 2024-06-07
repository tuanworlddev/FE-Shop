package com.dacs3.shop.ui.screens.product.create

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dacs3.shop.R
import com.dacs3.shop.component.AlertDialogNotification
import com.dacs3.shop.component.ButtonPrimary
import com.dacs3.shop.component.CategoryButton
import com.dacs3.shop.component.ColorButton
import com.dacs3.shop.component.ColorItemButton
import com.dacs3.shop.component.CustomTextField
import com.dacs3.shop.component.ErrorDialog
import com.dacs3.shop.component.MutableTextFiled
import com.dacs3.shop.component.RichText
import com.dacs3.shop.component.SizeButton
import com.dacs3.shop.component.SizeItemButton
import com.dacs3.shop.component.SpacerHeight
import com.dacs3.shop.component.SpacerWidth
import com.dacs3.shop.component.TopAppBarComponent
import com.dacs3.shop.model.Category
import com.dacs3.shop.ui.screens.product.details.ProductDetailsUiState
import com.dacs3.shop.ui.screens.product.details.ProductDetailsViewModel
import com.dacs3.shop.ui.screens.product.details.SizeBottomSheetModal
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2
import com.dacs3.shop.ui.theme.Primary100
import com.dacs3.shop.model.Color
import com.dacs3.shop.model.Size
import com.dacs3.shop.model.Variant
import com.dacs3.shop.ui.screens.loading.LoadingDialog
import com.dacs3.shop.ui.theme.BlackTransparent30
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CreateProductScreen(
    navController: NavHostController,
    viewModel: CreateProductViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val pickImage = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
        uris.let { uriList ->
            uriList.forEach { uri ->
                coroutineScope.launch {
                    val imageBitmap = withContext(Dispatchers.IO) {
                        context.contentResolver.openInputStream(uri)?.use { stream ->
                            BitmapFactory.decodeStream(stream)?.asImageBitmap()
                        }
                    }
                    imageBitmap?.let { image ->
                        viewModel.addImage(image)
                        viewModel.addImageUri(uri)
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.loadData()
    }

    val showCategoryModal = remember {
        mutableStateOf(false)
    }

    if (uiState.isLoading) {
        LoadingDialog(text = "Product creating")
    }

    if (uiState.errorMessage != null) {
        ErrorDialog(message = uiState.errorMessage!!) {
            viewModel.onErrorMessageChanged()
        }
    }

    if (uiState.createProductSuccess) {
        AlertDialogNotification(
            onDismissRequest = {
                viewModel.resetUiState()
                viewModel.onCreateProductSuccessChanged(false) }, dialogTitle = "Success", dialogText = "Product created successfully")
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .verticalScroll(rememberScrollState())
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
            CategoryButton(
                value = if (uiState.categorySelected != null) uiState.categorySelected!!.name!! else "Select Category",
                onClick = {
                    showCategoryModal.value = !showCategoryModal.value
                }
            )
            SpacerHeight(int = 15)
            Text(text = "Images", fontSize = 16.sp, fontWeight = FontWeight(450),color = Black50)
            SpacerHeight(int = 5)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(color = White, shape = RoundedCornerShape(8.dp))
                    .border(width = 1.dp, color = Light2, shape = RoundedCornerShape(8.dp))
                    .clip(shape = RoundedCornerShape(8.dp))
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (uiState.images.isNotEmpty()) {
                    uiState.images.forEachIndexed { index, image ->
                        ImageItem(image = image, onDelete = { viewModel.removeImage(index) })
                    }
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.default_ui_image),
                        contentDescription = null
                    )
                }
            }
            SpacerHeight(int = 10)
            OutlinedButton(
                onClick = {
                          pickImage.launch("image/*")
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Black100
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(painter = painterResource(id = R.drawable.upload), contentDescription = null)
                Text(text = "Upload Images")
            }
            SpacerHeight(int = 15)
            Text(text = "Items", fontSize = 16.sp, fontWeight = FontWeight(450),color = Black50)
            SpacerHeight(int = 10)
            Column {
                if (uiState.variants.isNotEmpty()) {
                    uiState.variants.forEachIndexed { index, variant ->
                        VariantItem(
                            variant = variant,
                            sizes = uiState.sizes,
                            colors = uiState.colors,
                            onVariantChange = {
                                viewModel.updateVariant(index, it)
                            }
                        )
                        SpacerHeight(int = 5)
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = Light2))
                        SpacerHeight(int = 5)
                    }
                }
            }
            OutlinedButton(
                onClick = { viewModel.addVariant() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Black100),
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Text(text = "Add Item")
            }
            SpacerHeight(int = 20)
            ButtonPrimary(
                text = "Create Product",
                onClick = { viewModel.createProduct() },
                modifier = Modifier.fillMaxWidth()
            )
            SpacerHeight(int = 20)
        }
    }

    if (uiState.categories.isNotEmpty()) {
        CategoryBottomModal(
            state = showCategoryModal,
            categories = uiState.categories,
            selected = uiState.categorySelected,
            onCategoryChange = {
                viewModel.onCategoryChanged(it)
            }
        )
    }
}

@Composable
fun ImageItem(image: ImageBitmap, onDelete: () -> Unit) {
    Box(
        modifier = Modifier.height(200.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Image(bitmap = image, contentDescription = null)
        IconButton(
            onClick = { onDelete() },
            modifier = Modifier.size(24.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = BlackTransparent30,
                contentColor = White
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "delete",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun VariantItem(
    variant: Variant,
    sizes: List<Size>,
    colors: List<Color>,
    onVariantChange: (Variant) -> Unit
) {

    val showSizeModal = remember {
        mutableStateOf(false)
    }
    val showColorModal = remember {
        mutableStateOf(false)
    }
    Column {
        SizeButton(value = variant.size?.name!!) {
            showSizeModal.value = true
        }
        SpacerHeight(int = 10)
        ColorButton(colorHex = variant.color?.value!!) {
            showColorModal.value = true
        }
        SpacerHeight(int = 10)
        CustomTextField(value = variant.price.toString(), onValueChange = { onVariantChange(variant.copy(price = it.toDoubleOrNull() ?: 0.0)) }, label = "Price")
        SpacerHeight(int = 10)
        CustomTextField(
            value = variant.quantity.toString(),
            onValueChange = {
                onVariantChange(variant.copy(quantity = it.toIntOrNull() ?: 0))
                            },
            label = "Quantity"
        )
        SpacerHeight(int = 10)
        CustomTextField(
            value = variant.sale.toString(),
            onValueChange = { onVariantChange(variant.copy(sale = it.toDoubleOrNull() ?: 0.0)) },
            label = "Sale (%)"
        )
    }

    SizeBottomModal(state = showSizeModal, sizes = sizes, selected = variant.size!!) {
        onVariantChange(variant.copy(size = it))
    }
    ColorBottomModal(showColorModal = showColorModal, colors = colors, selected = variant.color!!) {
        onVariantChange(variant.copy(color = it))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeBottomModal(state: MutableState<Boolean>, sizes: List<Size>, selected: Size, onSizeChange: (size: Size) -> Unit) {
    if (state.value) {
        ModalBottomSheet(
            onDismissRequest = { state.value = false },
            containerColor = White,
            modifier = Modifier
                .fillMaxWidth()
                .height(397.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .padding(horizontal = 15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Size",
                            fontSize = 24.sp,
                            fontWeight = FontWeight(700),
                            color = Black100
                        )
                        IconButton(onClick = { state.value = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                        }
                    }
                    SpacerHeight(int = 10)
                    LazyColumn {
                        items(sizes) {size ->
                            SizeItemButton(
                                value = size.name,
                                isSelected = selected.id == size.id
                            ) {
                                onSizeChange(size)
                                state.value = false
                            }
                            SpacerHeight(int = 8)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorBottomModal(showColorModal: MutableState<Boolean>, colors: List<Color>, selected: Color, onColorChange: (color: Color) -> Unit) {
    if (showColorModal.value) {
        ModalBottomSheet(
            onDismissRequest = { showColorModal.value = false },
            containerColor = White,
            modifier = Modifier
                .fillMaxWidth()
                .height(397.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .padding(horizontal = 15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Color",
                            fontSize = 24.sp,
                            fontWeight = FontWeight(700),
                            color = Black100
                        )
                        IconButton(onClick = { showColorModal.value = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                        }
                    }
                    SpacerHeight(int = 10)
                    LazyColumn {
                        items(colors) { color ->
                            ColorItemButton(
                                name = color.name,
                                colorHex = color.value,
                                isSelected = selected.id == color.id
                            ) {
                                onColorChange(color)
                                showColorModal.value = false
                            }
                            SpacerHeight(int = 8)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomModal(state: MutableState<Boolean>, categories: List<Category>, selected: Category?, onCategoryChange: (category: Category) -> Unit) {
    if (state.value) {
        ModalBottomSheet(
            onDismissRequest = { state.value = false },
            containerColor = White,
            modifier = Modifier
                .fillMaxWidth()
                .height(397.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .padding(horizontal = 15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Categories",
                            fontSize = 24.sp,
                            fontWeight = FontWeight(700),
                            color = Black100
                        )
                        IconButton(onClick = { state.value = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                        }
                    }
                    SpacerHeight(int = 10)
                    LazyColumn {
                        items(categories) {category ->
                            SizeItemButton(
                                value = category.name!!,
                                isSelected = if (selected != null) selected.id == category.id else false
                            ) {
                                onCategoryChange(category)
                                state.value = false
                            }
                            SpacerHeight(int = 8)
                        }
                    }
                }
            }
        }
    }
}
