package com.dacs3.shop.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.dacs3.shop.ui.theme.Black50
import com.dacs3.shop.ui.theme.Primary100

@Composable
fun BottomBarContainer(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.White,
        modifier = Modifier.height(70.dp)
    ) {
        var navSelected by remember {
            mutableIntStateOf(0)
        }
        NavItems.bottomNavItems.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = navSelected == index,
                onClick = {
                    navSelected = index
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.title,
                        tint = if (navSelected == index) Primary100 else Black50
                    )
                },
                label = { Text(text = navItem.title) },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Primary100,
                    selectedTextColor = Primary100,
                    indicatorColor = Color.White,
                    unselectedIconColor = Black50,
                    unselectedTextColor = Black50,
                    disabledIconColor = Color.White,
                    disabledTextColor = Color.White
                )
            )
        }
    }
}