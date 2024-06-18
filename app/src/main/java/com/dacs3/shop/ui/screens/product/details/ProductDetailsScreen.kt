package com.dacs3.shop.ui.screens.product.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.dacs3.shop.R
import com.dacs3.shop.component.AlertDialogNotification
import com.dacs3.shop.component.ColorButton
import com.dacs3.shop.component.ColorItemButton
import com.dacs3.shop.component.CommentCard
import com.dacs3.shop.component.CommentInput
import com.dacs3.shop.component.ErrorScreen
import com.dacs3.shop.component.QuantityButton
import com.dacs3.shop.component.SizeButton
import com.dacs3.shop.component.SizeItemButton
import com.dacs3.shop.component.SpacerHeight
import com.dacs3.shop.component.SpacerWidth
import com.dacs3.shop.component.shimmerEffect
import com.dacs3.shop.model.Image
import com.dacs3.shop.model.Product
import com.dacs3.shop.ui.screens.loading.LoadingScreen
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2
import com.dacs3.shop.ui.theme.MainPurple
import com.dacs3.shop.ui.theme.Primary100
import com.dacs3.shop.ui.theme.circularFont
import com.dacs3.shop.ui.theme.mainGray
import com.dacs3.shop.ui.theme.mainGrayStrong
import com.dacs3.shop.ui.theme.white
import java.math.BigDecimal
import java.util.Collections.addAll

@Composable
fun ProductDetailsScreen(productId: String?, navController: NavHostController, productDetailsViewModel: ProductDetailsViewModel = hiltViewModel()) {
    val uiState by productDetailsViewModel.productDetailsUiState.collectAsState()

    LaunchedEffect(Unit) {
        productId?.let {
            productDetailsViewModel.loadProduct(productId = productId)
        }
    }

    when {
        uiState.isLoading -> LoadingScreen()
        uiState.product != null -> ProductDetailsContent(uiState, productId!!.toInt(), navController, productDetailsViewModel)
    }

    if (uiState.isAddedToCart) {
        AlertDialogNotification(
            dialogTitle = "Success",
            dialogText = "Added to cart successfully",
            onDismissRequest = {
                productDetailsViewModel.onChangeIsAddedToCart(false)
            }
        )
    }

    if (uiState.isExists) {
        AlertDialogNotification(
            dialogTitle = "Notification",
            dialogText = "Product already exists in cart",
            onDismissRequest = {
                productDetailsViewModel.onChangeExists(false)
            }
        )
    }

    if (uiState.errorMessage.isNullOrEmpty()) {
        AlertDialogNotification(
            dialogTitle = "Error",
            dialogText = uiState.errorMessage!!,
            onDismissRequest = {
                productDetailsViewModel.onChangeExists(false)
            }
        )
    }
}

@Composable
fun ProductDetailsContent(uiState: ProductDetailsUiState, productId: Int, navController: NavHostController, productDetailsViewModel: ProductDetailsViewModel) {
    LaunchedEffect(Unit) {
        productDetailsViewModel.loadComments(productId)
    }
    val comments by productDetailsViewModel.comments.observeAsState(emptyList())
    val users by productDetailsViewModel.users.observeAsState(emptyMap())

    val showSizeModal = remember {
        mutableStateOf(false)
    }
    val showColorModal = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .verticalScroll(rememberScrollState())
            ) {
                TopBarProductDetails(navController = navController, onAddWishlist = {})
                SpacerHeight(int = 15)
                ProductImageContainer(images = uiState.product!!.images)
                SpacerHeight(int = 15)
                Text(
                    text = uiState.product.name,
                    fontSize = 18.sp,
                    fontFamily = circularFont,
                    fontWeight = FontWeight(600)
                )
                SpacerHeight(int = 10)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (uiState.currentVariant!!.sale!! > 0) {
                        Text(
                            text = "$${productDetailsViewModel.roundDouble(uiState.currentVariant.price!! * (1 - (uiState.currentVariant.sale!! / 100)))}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                            color = Primary100, fontFamily = circularFont
                        )
                        SpacerWidth(int = 15)
                        Text(
                            text = "$${uiState.currentVariant.price}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            color = Black100,
                            textDecoration = TextDecoration.LineThrough, fontFamily = circularFont
                        )
                    } else {
                        Text(
                            text = "$${uiState.currentVariant.price}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                            color = Primary100, fontFamily = circularFont
                        )
                    }
                }
                SpacerHeight(int = 24)
                SizeButton(value = uiState.currentVariant!!.size!!.name) {
                    showSizeModal.value = true
                }
                SpacerHeight(int = 15)
                ColorButton(colorHex = uiState.currentVariant.color!!.value) {
                    showColorModal.value = true
                }
                SpacerHeight(int = 15)
                QuantityButton(quantity = uiState.quantity, onReduce = { productDetailsViewModel.onReduce() }, onIncrease = { productDetailsViewModel.onIncrease() })
                SpacerHeight(int = 30)
                Text(
                    text = "Description",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Black100, fontFamily = circularFont
                )
                SpacerHeight(int = 20)
                Text(
                    text = uiState.product.description,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(450),
                    color = Black50, fontFamily = circularFont
                )
                SpacerHeight(int = 20)
                Text(
                    text = "Shipping & Returns",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Black100, fontFamily = circularFont
                )
                SpacerHeight(int = 20)
                Text(
                    text = "Free standard shipping and free 60-day returns",
                    fontSize = 12.sp,
                    fontWeight = FontWeight(700),
                    color = Black50, fontFamily = circularFont
                )
                SpacerHeight(int = 30)
                Text(
                    text = "Reviews",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Black100, fontFamily = circularFont
                )
                SpacerHeight(int = 20)

                if (comments.isEmpty()) {
                    Text(
                        text = "Comment not found",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(450),
                        color = Black50
                    )
                } else {
                    Text(
                        text = "(${comments.size}) Reviews",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(700),
                        color = Black50
                    )
                    SpacerHeight(int = 20)
                    comments.forEach { comment ->
                        val user = users[comment.userId]
                        user?.let {
                            CommentCard(user = user, content = comment.content ?: "")
                            SpacerHeight(int = 15)
                        }
                    }
                }
                Divider()
                SpacerHeight(int = 20)
                CommentInput(value = uiState.comment, onValueChange = { productDetailsViewModel.onCommentChanged(it) }) {
                    productDetailsViewModel.addComment()
                }
                SpacerHeight(int = 80)
            }
        }
        FloatingActionButton(
            onClick = {
                      if (!productDetailsViewModel.isUserExists()) {
                          navController.navigate("login")
                      } else {
                          productDetailsViewModel.onAddToCart()
                      }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(100.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(color = Primary100, shape = RoundedCornerShape(100.dp))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$${uiState.total}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Color.White, fontFamily = circularFont
                )
                Text(
                    text = "Add to Cart",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(450),
                    color = Color.White, fontFamily = circularFont
                )
            }
        }
    }
    SizeBottomSheetModal(uiState, showSizeModal, productDetailsViewModel)
    ColorBottomSheetModal(uiState, showColorModal, productDetailsViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeBottomSheetModal(uiState: ProductDetailsUiState, showSizeModal: MutableState<Boolean>,
                         productDetailsViewModel: ProductDetailsViewModel) {
    if (showSizeModal.value) {
        ModalBottomSheet(
            onDismissRequest = { showSizeModal.value = false },
            containerColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(397.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
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
                        IconButton(onClick = { showSizeModal.value = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "close")
                        }
                    }
                    SpacerHeight(int = 20)
                    LazyColumn {
                        val sizes = uiState.product!!.variants.map { it.size }.toSet().toList()
                        items(sizes) { size ->
                            SizeItemButton(value = size!!.name, isSelected = uiState.currentVariant!!.size == size) {
                                productDetailsViewModel.onSizeChange(sizeId = size.id)
                                showSizeModal.value = false
                            }
                            SpacerHeight(int = 16)
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun itemOfButtonSheet(string: String, check: Boolean, index2: Int, onToggle: (index1: Int) -> Unit) {
    Column {
        Row {
            SpacerWidth(int = 20)
            Row(modifier = Modifier
                .height(56.dp)
                .weight(1f)) {
                Button(onClick = { onToggle(index2) },
                    colors = ButtonDefaults.buttonColors(containerColor = if (!check) mainGray
                    else MainPurple), modifier = Modifier.fillMaxHeight()) {
                    if(check) {
                        Box(Modifier.width(250.dp)) {
                            Text(text = string, fontFamily = circularFont, fontSize = 15.sp, maxLines = 1,
                                overflow = TextOverflow.Ellipsis)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        androidx.compose.foundation.Image(painter = painterResource(id = R.drawable.choose),
                            contentDescription = null, Modifier.size(20.dp))
                    }else {
                        Box(Modifier.width(280.dp)) {
                            Text(text = string, fontFamily = circularFont, fontSize = 15.sp, color = Color.Black, maxLines = 1,
                                overflow = TextOverflow.Ellipsis)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            SpacerWidth(int = 20)
        }
        SpacerHeight(int = 16)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetItem(strings: List<String>, displayBottomSheet: MutableState<Boolean>,
                    size: MutableState<String>) {

    val scrollState = rememberScrollState()

    var booleanList = remember { mutableStateListOf<Boolean>().apply { addAll(strings.map { false }) } }

    if(displayBottomSheet.value){
        ModalBottomSheet(onDismissRequest = {displayBottomSheet.value = false}, containerColor = white) {
            Column(
                Modifier
                    .height(200.dp)
                    .verticalScroll(scrollState)) {
                for((index, item) in strings.withIndex()) {
                    if(index == 0) {
                        SpacerHeight(int = 10)
                    }
                    itemOfButtonSheet(string = item, check = booleanList[index], index2 = index,
                        onToggle = {index1 -> for((index, item) in booleanList.withIndex()) {
                            if(index == index1) {
                                booleanList[index] = true
                                size.value = strings[index]
                            }else {
                                booleanList[index] = false
                            }
                        }
                        })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorBottomSheetModal(uiState: ProductDetailsUiState, showColorModal: MutableState<Boolean>, productDetailsViewModel: ProductDetailsViewModel) {
    if (showColorModal.value) {
        val validColors = productDetailsViewModel.getColorsForSize(uiState.currentVariant!!.size!!.id)
        ModalBottomSheet(
            onDismissRequest = { showColorModal.value = false },
            containerColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(397.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
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
                    SpacerHeight(int = 20)
                    LazyColumn {
                        items(uiState.product!!.variants.map { it.color }
                            .distinctBy { it!!.id }) { color ->
                            val enabled = validColors.contains(color)
                            ColorItemButton(
                                name = color!!.name,
                                colorHex = color.value,
                                isSelected = uiState.currentVariant.color!!.id == color.id,
                                enabled = enabled
                            ) {
                                if (enabled) {
                                    productDetailsViewModel.onColorChange(colorId = color.id)
                                    showColorModal.value = false
                                }
                            }
                            SpacerHeight(int = 16)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarProductDetails(navController: NavHostController, onAddWishlist: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    SpacerHeight(int = 20)
    Row {
        androidx.compose.foundation.Image(painter = painterResource(id = R.drawable.iconback),
            contentDescription = null, modifier = Modifier
                .size(40.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { navController.popBackStack() })
        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color = colorResource(id = R.color.gray)), contentAlignment = Alignment.Center){
            androidx.compose.foundation.Image(painter = painterResource(id = R.drawable.heart),
                contentDescription = null, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun ProductImageContainer(images: List<Image>) {
    val scrollState = rememberScrollState()
    Row {
        Row(modifier = Modifier.horizontalScroll(scrollState)) {
            for (element in images) {
                Box(modifier = Modifier
                    .height(248.dp)
                    .width(161.dp)) {
                    SubcomposeAsyncImage(model = element.url, contentDescription = null,modifier = Modifier
                        .height(248.dp)
                        .width(161.dp), contentScale = ContentScale.Crop) {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Success) {
                            SubcomposeAsyncImageContent()
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(width = 161.dp, height = 248.dp)
                                    .shimmerEffect()
                            )
                        }

                    }

                }
                SpacerWidth(int = 10)
            }
        }
    }
}

@Composable
fun SpacerHeight(int: Int) {
    Spacer(modifier = Modifier.height(int.dp))
}

@Composable
fun SpacerWidth(int: Int) {
    Spacer(modifier = Modifier.width(int.dp))
}

@Composable
fun ButtonBackAndHeart() {
    Row {
        SpacerWidth(int = 20)
        androidx.compose.foundation.Image(painter = painterResource(id = R.drawable.iconback),
            contentDescription = null, modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color = colorResource(id = R.color.gray)), contentAlignment = Alignment.Center){
            androidx.compose.foundation.Image(painter = painterResource(id = R.drawable.heart),
                contentDescription = null, modifier = Modifier.size(16.dp))
        }
        SpacerWidth(int = 20)
    }
}

@Composable
fun ListImageProductDetail() {
    val scrollState = rememberScrollState()
    val imageList: List<Int> = listOf(R.drawable.topselling1, R.drawable.detail1,
        R.drawable.detail2)
    Row {
        SpacerWidth(int = 20)
        Row(modifier = Modifier.horizontalScroll(scrollState)) {
            for (element in imageList) {
                Box(modifier = Modifier
                    .height(248.dp)
                    .width(161.dp)) {
                    androidx.compose.foundation.Image(painter = painterResource(id = element),
                        contentDescription = null, modifier = Modifier
                            .height(248.dp)
                            .width(161.dp), contentScale = ContentScale.Crop
                    )

                }
                SpacerWidth(int = 10)
            }
        }
        SpacerWidth(int = 20)
    }
}

@Composable
fun NameProductAndPrice(name: String, price: Int) {
    Row {
        SpacerWidth(int = 20)
        Column {
            Text(
                text = name,
                fontFamily = circularFont,
                fontWeight = FontWeight(600),
                fontSize = 18.sp
            )
            SpacerHeight(int = 15)
            Row {
                Text(text = "$", fontFamily = circularFont, fontWeight = FontWeight(600), color = colorResource(
                    id = R.color.purple
                ))
                Text(text = price.toString(), fontFamily = circularFont, fontWeight = FontWeight(600), color = colorResource(
                    id = R.color.purple
                ))
            }
        }
        SpacerWidth(int = 20)
    }
}

@Composable
fun SelectionInProductDetail(onToggle: () -> Unit, size: String) {
    Row {
        SpacerWidth(int = 20)
        Box(modifier = Modifier
            .weight(1f)
            .height(56.dp), contentAlignment = Alignment.Center){
            Button(onClick = onToggle, modifier = Modifier
                .fillMaxSize(), colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                id = R.color.gray
            ))
            ) {

                Text(text = "Size", fontFamily = circularFont, style = TextStyle(fontSize = 16.sp), color = colorResource(
                    id = R.color.black
                ))
                Spacer(modifier = Modifier.weight(1f))
                Text(text = size, fontFamily = circularFont, fontSize = 16.sp,
                    fontWeight = FontWeight(600), color = colorResource(
                        id = R.color.black
                    ))
                SpacerWidth(int = 15)
                androidx.compose.foundation.Image(painter = painterResource(id = R.drawable.arrowdown2),
                    contentDescription = null, Modifier.size(22.dp))

            }
        }
        SpacerWidth(int = 20)
    }
}

@Composable
fun ButtonAddOrMinusResize(int: Int, size: Int, check: Boolean, onToggle: () -> Unit) {
    if(check) {
        Box(modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(color = colorResource(id = R.color.purple))
            .clickable { onToggle() }){
            androidx.compose.foundation.Image(painter = painterResource(id = int),
                contentDescription = null, modifier = Modifier
                    .align(Alignment.Center)
                    .size(14.dp))
        }
    }else {
        Box(modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(color = mainGrayStrong)){
            androidx.compose.foundation.Image(painter = painterResource(id = int),
                contentDescription = null, modifier = Modifier
                    .align(Alignment.Center)
                    .size(14.dp))
        }
    }
}

@Composable
fun ButtonQuantityInProductDetail(quantityTop: Int, quantity: MutableState<Int>) {
    Row {
        SpacerWidth(int = 20)
        Box(modifier = Modifier
            .weight(1f)
            .height(56.dp), contentAlignment = Alignment.Center){
            Button(onClick = { /*TODO*/ }, modifier = Modifier
                .fillMaxSize(), colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                id = R.color.gray
            )), interactionSource = NoRippleEffect()
            ) {

                Text(text = "Quantity", fontFamily = circularFont, style = TextStyle(fontSize = 16.sp), color = colorResource(
                    id = R.color.black
                ))
                Spacer(modifier = Modifier.weight(1f))
                if(quantity.value == 1) {
                    ButtonAddOrMinusResize(int = R.drawable.whiteaddicon, size = 40, true,
                        {quantity.value++})
                    SpacerWidth(int = 15)
                    Box(modifier = Modifier.width(12.dp)){
                        Text(text = quantity.value.toString(), fontFamily = circularFont, fontSize = 16.sp,
                            fontWeight = FontWeight(600), color = colorResource(
                                id = R.color.black
                            ))
                    }
                    SpacerWidth(int = 15)
                    ButtonAddOrMinusResize(int = R.drawable.minus, size = 40, false, {})
                }else if(quantity.value == quantityTop) {
                    ButtonAddOrMinusResize(int = R.drawable.whiteaddicon, size = 40, false,
                        {})
                    SpacerWidth(int = 15)
                    Box(modifier = Modifier.width(12.dp)){
                        Text(text = quantity.value.toString(), fontFamily = circularFont, fontSize = 16.sp,
                            fontWeight = FontWeight(600), color = colorResource(
                                id = R.color.black
                            ))
                    }
                    SpacerWidth(int = 15)
                    ButtonAddOrMinusResize(int = R.drawable.minus, size = 40, true, {quantity.value = quantity.value - 1})
                }else {
                    ButtonAddOrMinusResize(int = R.drawable.whiteaddicon, size = 40, true, {quantity.value = quantity.value + 1})
                    SpacerWidth(int = 15)
                    Box(modifier = Modifier.width(12.dp)){
                        Text(text = quantity.value.toString(), fontFamily = circularFont, fontSize = 16.sp,
                            fontWeight = FontWeight(600), color = colorResource(
                                id = R.color.black
                            ))
                    }
                    SpacerWidth(int = 15)
                    ButtonAddOrMinusResize(int = R.drawable.minus, size = 40, true, {quantity.value = quantity.value - 1})
                }

            }
        }
        SpacerWidth(int = 20)
    }
}

@Composable
fun AreaOfDescriptionInProductDetail(description: String) {
    Row {
        SpacerWidth(int = 20)
        Box(Modifier.weight(1f)){
            Text(text = description, fontSize = 14.sp, fontFamily = circularFont, color = colorResource(
                id = R.color.textGray
            ))
        }
        SpacerWidth(int = 20)
    }
}