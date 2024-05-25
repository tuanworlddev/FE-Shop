package com.dacs3.shop.component

import android.hardware.lights.Light
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Light2

@Composable
fun SpacerHeight(int: Int) {
    Spacer(modifier = Modifier.height(int.dp))
}

@Composable
fun SpacerWidth(int: Int) {
    Spacer(modifier = Modifier.width(int.dp))
}

@Composable
fun TopAppBarComponent(navController: NavHostController, title: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Light2
            ),
            modifier = Modifier.size(40.dp)
        )
        {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "back",
                tint = Black100,
                modifier = Modifier.size(24.dp)
            )
        }

       if (title != null) {
           Text(
               text = title,
               color = Black100,
               fontWeight = FontWeight(700),
               fontSize =  16.sp
           )

           Spacer(modifier = Modifier.weight(1f))
       }
    }
}