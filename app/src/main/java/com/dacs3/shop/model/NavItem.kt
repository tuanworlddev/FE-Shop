package com.dacs3.shop.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val title: String,
    @DrawableRes val icon: Int,
    val route: String
)
