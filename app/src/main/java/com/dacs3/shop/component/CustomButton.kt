package com.dacs3.shop.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Light2
import com.dacs3.shop.ui.theme.Primary100
import com.dacs3.shop.ui.theme.Primary50

@Composable
fun ButtonPrimary(
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary100,
            disabledContainerColor = Primary50
        ),
        shape = RoundedCornerShape(100.dp),
        enabled = enabled
    ) {
        Text(text = text)
    }
}

@Composable
fun ButtonLoginSocial(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    text: String
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Light2
        ),
        shape = RoundedCornerShape(100.dp),
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = Black100
        )
    }
}

@Composable
fun SizeButton(value: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Light2,
            contentColor = Black100
        ),
        shape = RoundedCornerShape(100.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Size",
                fontSize = 16.sp,
                fontWeight = FontWeight(450)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700)
                )
                SpacerWidth(int = 20)
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun ColorButton(colorHex: String, onClick: () -> Unit) {
    val color = Color(android.graphics.Color.parseColor(colorHex))

    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Light2,
            contentColor = Black100
        ),
        shape = RoundedCornerShape(100.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Color",
                fontSize = 16.sp,
                fontWeight = FontWeight(450)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(color = color)
                )
                SpacerWidth(int = 20)
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun SizeItemButton(value: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Primary100 else Light2,
            contentColor = if (isSelected) White else Black100
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight(500)
            )
            if (isSelected) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "check")
            }
        }
    }
}

@Composable
fun ColorItemButton(name: String, colorHex: String, isSelected: Boolean, enabled: Boolean = true, onClick: () -> Unit) {
    val color = Color(android.graphics.Color.parseColor(colorHex))

    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Primary100 else Light2,
            contentColor = if (isSelected) White else Black100
        ),
        enabled = enabled
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight(500)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(color = color)
                    .border(width = 2.dp, color = White, shape = CircleShape)
                )
                SpacerWidth(int = 20)
                Icon(imageVector = Icons.Default.Check, contentDescription = "check", tint = if (isSelected) White else Color.Transparent)
            }
        }
    }
}

@Composable
fun QuantityButton(quantity: Int, onReduce: () -> Unit, onIncrease: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = Light2, shape = RoundedCornerShape(100.dp))
            .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Quantity",
                fontSize = 16.sp,
                fontWeight = FontWeight(450),
                color = Black100
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onReduce() },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Primary100,
                        contentColor = White
                    ),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(
                        modifier = Modifier.size(width = 10.dp, height = 1.dp)
                            .background(color = White, shape = RoundedCornerShape(100.dp))
                    )
                }
                SpacerWidth(int = 20)
                Text(
                    text = quantity.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight(450),
                    color = Black100
                )
                SpacerWidth(int = 20)
                IconButton(
                    onClick = { onIncrease() },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Primary100,
                        contentColor = White
                    ),
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "add", modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuantityButtonPreview() {
    QuantityButton(1, onIncrease = {}, onReduce = {})
}

