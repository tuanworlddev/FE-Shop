package com.dacs3.shop.ui.screens.address.update

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dacs3.shop.component.AlertDanger
import com.dacs3.shop.component.AlertSuccess
import com.dacs3.shop.component.ButtonPrimary
import com.dacs3.shop.component.CustomTextField
import com.dacs3.shop.component.SpacerHeight
import com.dacs3.shop.component.TopAppBarComponent
import com.dacs3.shop.ui.screens.loading.LoadingDialog
import com.dacs3.shop.ui.screens.loading.LoadingScreen
import com.dacs3.shop.ui.theme.Black100

@Composable
fun AddressUpdateScreen(
    addressId: String?,
    navController: NavHostController,
    viewModel: AddressUpdateViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        addressId?.let {
            viewModel.loadAddress(it.toInt())
        }
    }

    if (uiState.isLoading) {
        LoadingScreen()
    } else {
        if (uiState.isUpdating) {
            LoadingDialog(text = "Updating Address")
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
            ) {
                SpacerHeight(int = 10)
                TopAppBarComponent(navController = navController, title = "Update Address")
                SpacerHeight(int = 15)
                if (uiState.successMessage != null) {
                    AlertSuccess(text = uiState.successMessage!!)
                    SpacerHeight(int = 15)
                }
                if (uiState.errorMessage != null) {
                    AlertDanger(text = uiState.errorMessage!!)
                    SpacerHeight(int = 15)
                }
                if (uiState.address != null) {
                    CustomTextField(value = uiState.address?.country!!, onValueChange = { viewModel.onCountryChanged(it) }, label = "Country")
                    SpacerHeight(int = 10)
                    CustomTextField(value = uiState.address?.province!!, onValueChange = { viewModel.onProvinceChanged(it) }, label = "Province/City")
                    SpacerHeight(int = 10)
                    CustomTextField(value = uiState.address?.district!!, onValueChange = { viewModel.onDistrictChanged(it) }, label = "District")
                    SpacerHeight(int = 10)
                    CustomTextField(value = uiState.address?.commune!!, onValueChange = { viewModel.onCommuneChanged(it) }, label = "Commune/Ward")
                    SpacerHeight(int = 10)
                    CustomTextField(value = uiState.address?.addressLine!!, onValueChange = { viewModel.onAddressLineChanged(it) }, label = "Street Address")
                    SpacerHeight(int = 10)
                    CustomTextField(value = uiState.address?.phoneNumber!!, onValueChange = { viewModel.onPhoneNumberChanged(it) }, label = "Phone Number")
                    SpacerHeight(int = 5)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = uiState.address?.isDefault!!,
                            onCheckedChange = { viewModel.onIsDefaultChanged(it) }
                        )
                        Text(text = "Default", color = Black100)
                    }
                    SpacerHeight(int = 10)
                    ButtonPrimary(onClick = { viewModel.onUpdateAddress() }, text = "Save", modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}