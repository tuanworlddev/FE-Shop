package com.dacs3.shop.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.dacs3.shop.R
import com.dacs3.shop.model.Product
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Light2
import java.math.BigDecimal

@Composable
fun ProductCard(product: Product, navHostController: NavHostController) {
    val price = product.price ?: BigDecimal.ZERO
    val sale: Float = product.sale ?: 0f

    Column(
        modifier = Modifier
            .width(159.dp)
            .background(color = Light2, shape = RoundedCornerShape(8.dp))
            .clickable { navHostController.navigate("product-details/${product.id}") }
    ) {
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            SubcomposeAsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier.clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                    CircularProgressIndicator(
                        strokeWidth = 1.dp
                    )
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
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
                    modifier = Modifier.size(28.dp)
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }
        Column(
            modifier = Modifier.padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 16.dp)
        ) {
            Text(
                text = product.name!!,
                fontSize = 12.sp,
                fontWeight = FontWeight(450),
                color = Black100,
                maxLines = 1
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (sale > 0f) {
                    Text(
                        text = "$${"%.2f".format(price.toFloat() * (1 - sale / 100))}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(700),
                        color = Black100
                    )
                    Text(
                        text = "$${price}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(700),
                        color = Black100,
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
