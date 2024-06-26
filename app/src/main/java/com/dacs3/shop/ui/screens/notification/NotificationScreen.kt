package com.dacs3.shop.ui.screens.notification

import android.app.Notification
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dacs3.shop.R
import com.dacs3.shop.component.ButtonPrimary
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.circularFont
import com.dacs3.shop.ui.theme.white

@Composable
fun NotificationScreen(navController: NavHostController) {
    NoNotificationScreen(onCategoryScreenChange = { navController.navigate("categories") })
}

@Composable
fun NoNotificationScreen(onCategoryScreenChange: () -> Unit) {
    Surface(color = white,
        modifier = Modifier.fillMaxSize()

    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.notifications),
                fontSize =  16.sp,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Black100, fontFamily = circularFont
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(id = R.drawable.bell_1), contentDescription = "notification", modifier = Modifier.size(100.dp))
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(text = "No Notification yet", fontSize = 24.sp, color = Black100,
                        fontFamily = circularFont)
                    Spacer(modifier = Modifier.height(15.dp))
                    ButtonPrimary(onClick = { onCategoryScreenChange() }, text = "Explore Categories")
                }
            }
        }
    }
}

