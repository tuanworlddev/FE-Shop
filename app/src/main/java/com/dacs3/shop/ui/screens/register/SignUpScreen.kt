package com.dacs3.shop.ui.screens.register

import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dacs3.shop.R
import com.dacs3.shop.component.AlertDialogNotification
import com.dacs3.shop.component.ButtonLoginSocial
import com.dacs3.shop.component.ButtonPrimary
import com.dacs3.shop.component.CustomPasswordField
import com.dacs3.shop.component.CustomTextField
import com.dacs3.shop.ui.theme.Black100

@Composable
fun SignUpScreen(navController: NavHostController, signUpViewModel: SignUpViewModel = hiltViewModel()) {
    val uiState by signUpViewModel.signUpUiState.collectAsState()

    if (uiState.signUpSuccess) {
        navController.navigate("login")
    }

    if (uiState.errorMessage != null) {
        AlertDialogNotification(
            onDismissRequest = { signUpViewModel.onErrorMessageChange(null) },
            dialogTitle = "System error",
            dialogText = uiState.errorMessage!!
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .background(Color.White)
    ) {
        Text(
            text = stringResource(id = R.string.create_account),
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            fontWeight = FontWeight.Bold,
            fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
            color = Black100
        )

        Spacer(modifier = Modifier.height(30.dp))

        CustomTextField(value = uiState.firstName, onValueChange = { signUpViewModel.onFirstNameChange(it) }, placeholder = stringResource(id = R.string.first_name), errorMessage = uiState.firstNameError, enabled = !uiState.isLoading)

        Spacer(modifier = Modifier.height(10.dp))

        CustomTextField(value = uiState.lastName, onValueChange = { signUpViewModel.onLastNameChange(it) }, placeholder = stringResource(id = R.string.last_name), errorMessage = uiState.lastNameError, enabled = !uiState.isLoading)

        Spacer(modifier = Modifier.height(10.dp))

        CustomTextField(value = uiState.email, onValueChange = { signUpViewModel.onEmailChange(it) }, placeholder = stringResource(id = R.string.email_address), errorMessage = uiState.emailError, enabled = !uiState.isLoading)

        Spacer(modifier = Modifier.height(10.dp))

        CustomPasswordField(value = uiState.password, onValueChange = { signUpViewModel.onPasswordChange(it) }, placeholder = stringResource(id = R.string.password), errorMessage = uiState.passwordError, enabled = !uiState.isLoading)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "Forgot password ?", color = Black100, fontSize = 14.sp)
            TextButton(
                onClick = { navController.navigate("reset-password") },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Black100
                )
            ) {
                Text(text = "Reset", fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ButtonPrimary(
            onClick = {
                try {
                    signUpViewModel.signUp()
                } catch (e: Exception) {
                    signUpViewModel.onErrorMessageChange(e.message)
                }
                      },
            text = stringResource(id = R.string.sign_up),
            enabled = !uiState.isLoading
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "Do you have an account ?", color = Black100, fontSize = 14.sp)
            TextButton(
                onClick = { navController.navigate("login") },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Black100
                )
            ) {
                Text(text = "Sign in", fontSize = 14.sp)
            }
        }

    }
}

