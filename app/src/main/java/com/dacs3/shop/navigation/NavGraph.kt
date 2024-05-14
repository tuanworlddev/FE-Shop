package com.dacs3.shop.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dacs3.shop.ui.screens.home.HomeScreen
import com.dacs3.shop.ui.screens.login.LoginScreen
import com.dacs3.shop.ui.screens.notification.NotificationScreen
import com.dacs3.shop.ui.screens.order.OrderScreen
import com.dacs3.shop.ui.screens.register.SignUpScreen

@Composable
fun NavGraphContainer(paddingValues: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(
            route = "home",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(200)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(200)
                )
            }
        ) {
            HomeScreen()
        }
        composable(
            route = "notification",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(200)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(200)
                )
            }
        ) {
            NotificationScreen()
        }
        composable(
            route = "order",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(200)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(200)
                )
            }
        ) {
            OrderScreen()
        }
        composable(
            route = "account",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(200)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(200)
                )
            }
        ) {
            LoginScreen(navController)
        }
        composable(
            route = "login",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(200)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(200)
                )
            }
        ) {
            LoginScreen(navController)
        }
        composable("signup") {
            SignUpScreen(navController)
        }
    }
}