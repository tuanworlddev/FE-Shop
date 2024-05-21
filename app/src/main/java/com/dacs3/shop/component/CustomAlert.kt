package com.dacs3.shop.component

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dacs3.shop.R

@Composable
fun BaseAlert(
    text: String,
    @ColorRes backgroundColor: Int,
    @ColorRes borderColor: Int,
    @ColorRes textColor: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = backgroundColor),
                shape = RoundedCornerShape(6.dp)
            )
            .border(
                width = 1.dp,
                color = colorResource(id = borderColor),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = text,
            color = colorResource(id = textColor),
            fontSize = MaterialTheme.typography.bodyMedium.fontSize
        )
    }
}

@Composable
fun AlertDanger(
    text: String
) {
    BaseAlert(text = text, backgroundColor = R.color.red_container, borderColor = R.color.red_border, textColor = R.color.red_text)
}

@Preview(showBackground = true)
@Composable
fun BaseAlertPreview() {
    BaseAlert(text = "Hello", backgroundColor = R.color.red_container, borderColor = R.color.red_border, textColor = R.color.red_text)
}