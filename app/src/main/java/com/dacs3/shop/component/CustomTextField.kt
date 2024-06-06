package com.dacs3.shop.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2
import com.dacs3.shop.ui.theme.Primary100

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String? = null,
    enabled: Boolean = true
) {
    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(text = label, color = Black50)
            },
            singleLine = true,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Black100,
                unfocusedTextColor = Black100,
                disabledTextColor = Black50,
                focusedContainerColor = Light2,
                unfocusedContainerColor = Light2,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Black100
            ),
            isError = errorMessage != null,
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
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

@Composable
fun CustomPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String? = null,
    enabled: Boolean = true
) {
    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(text = label, color = Black50)
            },
            singleLine = true,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Black100,
                unfocusedTextColor = Black100,
                disabledTextColor = Black50,
                focusedContainerColor = Light2,
                unfocusedContainerColor = Light2,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Black100
            ),
            isError = errorMessage != null,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            enabled = enabled
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
fun MutableTextFiled(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String? = null,
    enabled: Boolean = true
) {
    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(text = label, color = Black50)
            },
            minLines = 3,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(168.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Black100,
                unfocusedTextColor = Black100,
                disabledTextColor = Black50,
                focusedContainerColor = Light2,
                unfocusedContainerColor = Light2,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Black100
            ),
            isError = errorMessage != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default
            ),
            enabled = enabled
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
fun RichText(text: String) {
    val annotatedString = buildAnnotatedString {
        val words = text.split(" ")
        for (word in words) {
            when {
                word.startsWith("**") && word.endsWith("**") -> {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(word.trim('*'))
                    }
                }
                word.startsWith("*") && word.endsWith("*") -> {
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append(word.trim('*'))
                    }
                }
                else -> append(word)
            }
            append(" ")
        }
    }

    Text(annotatedString)
}

