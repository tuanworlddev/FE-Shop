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
            icon = R.drawable.ic_home,
            route = "home"
        ),
        NavItem(
            "Notification",
            icon = R.drawable.ic_notification,
            route = "notification"
        ),
        NavItem(
            "Order",
            icon = R.drawable.ic_order,
            route = "order"
        ),
        NavItem(
            "Account",
            icon = R.drawable.ic_account,
            route = "account"
        )
    )
}