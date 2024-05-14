package com.dacs3.shop.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    errorMessage: String? = null
) {
    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = placeholder, color = Black50)
            },
            singleLine = true,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Black50,
                focusedContainerColor = Light2,
                unfocusedContainerColor = Light2,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            isError = errorMessage != null
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                color = Color.Red
            )
        }
    }
}

@Composable
fun CustomPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    errorMessage: String? = null
) {
    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = placeholder, color = Black50)
            },
            singleLine = true,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Black50,
                focusedContainerColor = Light2,
                unfocusedContainerColor = Light2,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            isError = errorMessage != null,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                color = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    CustomTextField(
        value = "",
        onValueChange = {},
        placeholder = "Address",
        errorMessage = "Name is required"
    )
}

@Preview(showBackground = true)
@Composable
fun CustomPasswordFieldPreview() {
    CustomPasswordField(
        value = "",
        onValueChange = {},
        placeholder = "Password",
        errorMessage = "Password is required"
    )
}
