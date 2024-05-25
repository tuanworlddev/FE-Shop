package com.dacs3.shop.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.dacs3.shop.R
import com.dacs3.shop.model.Product
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2
import java.math.BigDecimal

@Composable
fun ProductCard(product: Product, navHostController: NavHostController) {
    val firstVariant = product.variants.firstOrNull()
    if (firstVariant != null) {
        val price = firstVariant.price
        val sale: Double = firstVariant.sale
        var isLoading by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .size(width = 159.dp, height = 281.dp)
                .background(color = Light2, shape = RoundedCornerShape(8.dp))
                .clickable(enabled = !isLoading) {
                    navHostController.navigate("product-details/${product.id}")
                }
        ) {
            Box(
                contentAlignment = Alignment.TopCenter
            ) {
                SubcomposeAsyncImage(
                    model = product.images.first().url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Success) {
                        isLoading = false
                        SubcomposeAsyncImageContent()
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                .shimmerEffect()
                        )
                    }
                }
                if (!isLoading) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = if (sale > 0) painterResource(id = R.drawable.product_sale) else painterResource(id = R.drawable.product_new),
                            contentDescription = null
                        )
                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
            if (isLoading) {
                Box(modifier = Modifier.padding(4.dp)) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .shimmerEffect()
                        )
                    }
                }
            } else {
                Column(modifier = Modifier.padding(4.dp)) {
                    Text(
                        text = product.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(450),
                        color = Black100,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        if (sale > 0f) {
                            Text(
                                text = "$${"%.2f".format(price.toFloat() * (1 - sale / 100))}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight(700),
                                color = Black100
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "$${price}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight(500),
                                color = Black50,
                                textDecoration = TextDecoration.LineThrough
                            )
                        } else {
                            Text(
                                text = "$${price}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight(700),
                                color = Black100
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Modifier.shimmerEffect(): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "")
    val gradientWidth = 2000f
    val xOffset by transition.animateFloat(
        initialValue = -gradientWidth,
        targetValue = gradientWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        ), label = ""
    )

    val shimmerColors = listOf(
        Color(0xFFE2E2E2),
        Color(0xFFC7C7C7),
        Color(0xFFD7D6D6)
    )

    background(
        brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(xOffset, 0f),
            end = Offset(xOffset + gradientWidth, 0f)
        )
    )
}

