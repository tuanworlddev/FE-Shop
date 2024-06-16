package com.dacs3.shop.ui.screens.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dacs3.shop.component.ButtonLink
import com.dacs3.shop.component.ButtonPrimary
import com.dacs3.shop.component.SpacerHeight
import com.dacs3.shop.component.TopAppBarComponent
import com.dacs3.shop.model.Address
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Light2
import com.dacs3.shop.ui.theme.Primary100
import com.dacs3.shop.ui.theme.circularFont

@Composable
fun CheckoutScreen(navController: NavHostController, viewModel: CheckoutViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadCarts()
        viewModel.loadAddresses()
    }

    if (uiState.isOrderSuccess) {
        navController.navigate("order-success")
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
            ) {
                SpacerHeight(int = 10)
                TopAppBarComponent(navController = navController, title = "Carts")
                SpacerHeight(int = 15)
                if (uiState.carts.isEmpty()) {
                    Text(
                        text = "No cart",
                        color = Black50,
                        fontWeight = FontWeight(450),
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    ButtonLink(label = "Shipping Address", content = if (uiState.selectedAddress == null) "Add Shipping Address" else "${uiState.selectedAddress?.addressLine}, ${uiState.selectedAddress?.commune}, ${uiState.selectedAddress?.district}, ${uiState.selectedAddress?.province}, ${uiState.selectedAddress?.country}") {
                        showDialog = true
                    }
                    SpacerHeight(int = 10)
                    ButtonLink(label = "Payment Method", content = "Add Payment Method") {

                    }
                    SpacerHeight(int = 150)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Subtotal",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black50
                        )
                        Text(
                            text = "$${uiState.subtotal}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black100
                        )
                    }
                    SpacerHeight(int = 8)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Shipping Cost",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black50
                        )
                        Text(
                            text = "$${uiState.shippingCost}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black100
                        )
                    }
                    SpacerHeight(int = 8)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Tax",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black50
                        )
                        Text(
                            text = "$${uiState.tax}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black100
                        )
                    }
                    SpacerHeight(int = 8)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black50
                        )
                        Text(
                            text = "$${uiState.total}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(450),
                            color = Black100
                        )
                    }

                    SpacerHeight(int = 50)
                    Button(
                        onClick = {
                                  viewModel.onOrderClick()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(100.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary100
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "$${uiState.total}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight(700),
                                color = Color.White, fontFamily = circularFont
                            )
                            Text(
                                text = "Place Order",
                                fontSize = 16.sp,
                                fontWeight = FontWeight(450),
                                color = Color.White, fontFamily = circularFont
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddressSelectionDialog(
            addresses = uiState.addresses,
            onSelect = { address ->
                viewModel.selectAddress(address)
                showDialog = false
            },
            onDismiss = { showDialog = false },
            onNewAddress = {
                showDialog = false
                navController.navigate("create-address")
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressSelectionDialog(
    addresses: List<Address>,
    onSelect: (Address) -> Unit,
    onDismiss: () -> Unit,
    onNewAddress: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = { Text("Select Shipping Address") },
        text = {
            Column {
                addresses.forEach { address ->
                    Card(
                        onClick = { onSelect(address) },
                        colors = CardDefaults.cardColors(
                            containerColor = Light2
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Text(text = "Phone: ${address.phoneNumber}")
                            Text(
                                text = "${address.addressLine}, ${address.commune}, ${address.district}, ${address.province}, ${address.country}",
                                modifier = Modifier
                                    .fillMaxWidth(),
                                maxLines = 1,
                                color = Black100,
                                fontSize = 14.sp,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Button(
                    onClick = { onNewAddress() },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
                    Text("Add New Address")
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}
