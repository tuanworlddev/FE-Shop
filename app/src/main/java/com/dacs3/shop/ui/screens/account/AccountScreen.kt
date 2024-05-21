package com.dacs3.shop.ui.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dacs3.shop.R
import com.dacs3.shop.component.AlertDialogNotification
import com.dacs3.shop.component.ButtonPrimary
import com.dacs3.shop.component.CardLinkItem
import com.dacs3.shop.model.User
import com.dacs3.shop.ui.screens.login.LoginScreen
import com.dacs3.shop.ui.theme.Black100
import com.dacs3.shop.ui.theme.Primary100
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AccountScreen(navController: NavHostController, accountViewModel: AccountViewModel = hiltViewModel()) {
    val uiState by accountViewModel.accountUiState.collectAsState()

    LaunchedEffect(Unit) {
        if (uiState.user == null) {
            accountViewModel.loadUser()
        }
    }

    if (uiState.errorMessage != null) {
        AlertDialogNotification(
            onDismissRequest = {
                accountViewModel.onChangeErrorMessage(null)
            },
            dialogTitle = "System Error",
            dialogText = uiState.errorMessage!!
        )
    }

    if (uiState.isLoggedIn) {
        LoggedInScreen(user = uiState.user, accountViewModel, navController)
    } else {
        NotLoggedInScreen(onNavigationLogin = { navController.navigate("login") })
    }
}

@Composable
fun NotLoggedInScreen(onNavigationLogin: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.ic_send_email), contentDescription = "", modifier = Modifier.size(100.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "We sent you an email to reset your password.", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(20.dp))
            ButtonPrimary(onClick = { onNavigationLogin() }, text = "Return to Login")
        }
    }
}

@Composable
fun LoggedInScreen(user: User?, accountViewModel: AccountViewModel, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 24.dp,
                        bottomEnd = 24.dp
                    )
                )
                .background(color = Primary100),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = user?.avatar),
                    contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(shape = CircleShape)
                        .border(width = 2.dp, color = Color.White, shape = CircleShape)
                )

                Spacer(modifier = Modifier.width(15.dp))

                Column {
                    Text(text = "${user?.lastName} ${user?.firstName}", color = Color.White)
                    Text(text = user?.email!!, color = Color.White)
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Black100
                        )
                    ) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit", modifier = Modifier.size(14.dp))
                        Text(text = "  Edit profile", fontSize = 11.sp)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(89.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_confirmation), contentDescription = "confirmation", tint = Primary100, modifier = Modifier.size(30.dp))
                Text(text = "Confirmation", fontSize = 9.sp, color = colorResource(id = R.color.black_555))
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_pickup), contentDescription = "pickup", tint = Primary100, modifier = Modifier.size(30.dp))
                Text(text = "Pickup", fontSize = 9.sp, color = colorResource(id = R.color.black_555))
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_delivery), contentDescription = "delivery", tint = Primary100, modifier = Modifier.size(30.dp))
                Text(text = "Delivery", fontSize = 9.sp, color = colorResource(id = R.color.black_555))
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_rating), contentDescription = "rating", tint = Primary100, modifier = Modifier.size(30.dp))
                Text(text = "Rating", fontSize = 9.sp, color = colorResource(id = R.color.black_555))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Column {
                if (user?.role == "ADMIN") {
                    CardLinkItem(title = "Product Management") {
                        navController.navigate("product-management")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    CardLinkItem(title = "Category Management") {
                        navController.navigate("category-management")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                CardLinkItem(title = "Address") {

                }
                Spacer(modifier = Modifier.height(8.dp))
                CardLinkItem(title = "Wishlist") {

                }
                Spacer(modifier = Modifier.height(8.dp))
                CardLinkItem(title = "Payment") {

                }
                Spacer(modifier = Modifier.height(8.dp))
                CardLinkItem(title = "Help") {

                }
                Spacer(modifier = Modifier.height(8.dp))
                CardLinkItem(title = "Support") {
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { accountViewModel.logout() }
                    ) {
                        Text(text = "Sign Out", fontWeight = FontWeight(700), fontSize = 16.sp, color = Color.Red)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
