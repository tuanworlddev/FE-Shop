package com.dacs3.shop.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dacs3.shop.R
import com.dacs3.shop.component.ButtonLoginSocial
import com.dacs3.shop.component.ButtonPrimary
import com.dacs3.shop.component.CustomPasswordField
import com.dacs3.shop.component.CustomTextField
import com.dacs3.shop.ui.theme.Black100

@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.sign_in),
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            fontWeight = FontWeight.Bold,
            fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
            color = Black100
        )

        Spacer(modifier = Modifier.height(30.dp))

        CustomTextField(value = "", onValueChange = {}, placeholder = stringResource(id = R.string.email_address))

        Spacer(modifier = Modifier.height(10.dp))

        CustomPasswordField(value = "", onValueChange = {}, placeholder = stringResource(id = R.string.password))

        Spacer(modifier = Modifier.height(20.dp))

        ButtonPrimary(onClick = { /*TODO*/ }, text = stringResource(id = R.string.sign_in))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "Don't have an account ? ", color = Black100, fontSize = 14.sp)
            TextButton(
                onClick = { navController.navigate("signup") },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Black100
                )
            ) {
                Text(text = "Create One", fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        ButtonLoginSocial(onClick = { /*TODO*/ }, icon = R.drawable.ic_apple, text = "Continue With Apple")

        Spacer(modifier = Modifier.height(20.dp))

        ButtonLoginSocial(onClick = { /*TODO*/ }, icon = R.drawable.ic_google, text = "Continue With Google")

        Spacer(modifier = Modifier.height(20.dp))

        ButtonLoginSocial(onClick = { /*TODO*/ }, icon = R.drawable.ic_facebook, text = "Continue With Facebook")

    }
}

