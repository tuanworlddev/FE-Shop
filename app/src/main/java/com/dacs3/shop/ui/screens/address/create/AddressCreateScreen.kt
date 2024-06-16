package com.dacs3.shop.ui.screens.address.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.dacs3.shop.ui.theme.Black100

@Composable
fun AddressCreateScreen(navController: NavHostController, viewModel: AddressCreateViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

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
            TopAppBarComponent(navController = navController, title = "Add Address")
            SpacerHeight(int = 15)
            if (uiState.successMessage != null) {
                AlertSuccess(text = uiState.successMessage!!)
                SpacerHeight(int = 15)
            }
            if (uiState.errorMessage != null) {
                AlertDanger(text = uiState.errorMessage!!)
                SpacerHeight(int = 15)
            }
            CustomTextField(value = uiState.country, onValueChange = { viewModel.onCountryChanged(it) }, label = "Country")
            SpacerHeight(int = 10)
            CustomTextField(value = uiState.province, onValueChange = { viewModel.onProvinceChanged(it) }, label = "Province/City")
            SpacerHeight(int = 10)
            CustomTextField(value = uiState.district, onValueChange = { viewModel.onDistrictChanged(it) }, label = "District")
            SpacerHeight(int = 10)
            CustomTextField(value = uiState.commune, onValueChange = { viewModel.onCommuneChanged(it) }, label = "Commune/Ward")
            SpacerHeight(int = 10)
            CustomTextField(value = uiState.addressLine, onValueChange = { viewModel.onAddressLineChanged(it) }, label = "Street Address")
            SpacerHeight(int = 10)
            CustomTextField(value = uiState.phoneNumber, onValueChange = { viewModel.onPhoneNumberChanged(it) }, label = "Phone Number")
            SpacerHeight(int = 5)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = uiState.isDefault,
                    onCheckedChange = { viewModel.onIsDefaultChanged(it) }
                )
                Text(text = "Default", color = Black100)
            }
            SpacerHeight(int = 10)
            ButtonPrimary(onClick = { viewModel.onCreateAddress() }, text = "Save", modifier = Modifier.fillMaxWidth())
        }
    }
}