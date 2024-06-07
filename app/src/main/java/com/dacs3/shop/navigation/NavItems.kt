package com.dacs3.shop.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import com.dacs3.shop.R
import com.dacs3.shop.model.NavItem

object NavItems {
    val bottomNavItems = listOf(
        NavItem(
            "Home",
            icon = R.drawable.homemain,
            route = "home"
        ),
        NavItem(
            "Notification",
            icon = R.drawable.notimain,
            route = "notification"
        ),
        NavItem(
            "Order",
            icon = R.drawable.receipt1,
            route = "order"
        ),
        NavItem(
            "Account",
            icon = R.drawable.profilemain,
            route = "account"
        )
    )
}