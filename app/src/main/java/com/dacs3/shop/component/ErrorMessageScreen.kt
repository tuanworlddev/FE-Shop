package com.dacs3.shop.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun ErrorScreen(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, color = Color.Red, fontSize = 18.sp)
    }
}

@Composable
fun ErrorDialog(message: String, onDismissRequest: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = { /*TODO*/ },
        title = { Text(text = "Error") },
        text = { Text(text = message) },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "OK")
            }
        }
    )
}