package com.dacs3.shop.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import com.dacs3.shop.model.NavItem

object NavItems {
    val bottomNavItems = listOf(
        NavItem(
            "Home",
            icon = Icons.Outlined.Home,
            route = "home"
        ),
        NavItem(
            "Notification",
            icon = Icons.Outlined.Notifications,
            route = "notification"
        ),
        NavItem(
            "Order",
            icon = Icons.Outlined.CheckCircle,
            route = "order"
        ),
        NavItem(
            "Account",
            icon = Icons.Outlined.Person,
            route = "account"
        )
    )
}