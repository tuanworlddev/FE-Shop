package com.dacs3.shop.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dacs3.shop.ui.screens.account.AccountScreen
import com.dacs3.shop.ui.screens.address.create.AddressCreateScreen
import com.dacs3.shop.ui.screens.address.management.AddressManagementScreen
import com.dacs3.shop.ui.screens.address.update.AddressUpdateScreen
import com.dacs3.shop.ui.screens.cart.CartScreen
import com.dacs3.shop.ui.screens.category.CategoryScreen
import com.dacs3.shop.ui.screens.category.create.CreateCategoryScreen
import com.dacs3.shop.ui.screens.category.details.CategoryDetailsScreen
import com.dacs3.shop.ui.screens.category.management.CategoryManageScreen
import com.dacs3.shop.ui.screens.category.update.CategoryUpdateScreen
import com.dacs3.shop.ui.screens.checkout.CheckoutScreen
import com.dacs3.shop.ui.screens.home.HomeScreen
import com.dacs3.shop.ui.screens.login.LoginScreen
import com.dacs3.shop.ui.screens.notification.NotificationScreen
import com.dacs3.shop.ui.screens.order.OrderScreen
import com.dacs3.shop.ui.screens.order.OrderSuccessScreen
import com.dacs3.shop.ui.screens.order.details.OrderDetailsScreen
import com.dacs3.shop.ui.screens.order.item.OrderItemScreen
import com.dacs3.shop.ui.screens.order.manage.OrderMangerScreen
import com.dacs3.shop.ui.screens.product.create.CreateProductScreen
import com.dacs3.shop.ui.screens.product.details.ProductDetailsScreen
import com.dacs3.shop.ui.screens.product.management.ProductManageScreen
import com.dacs3.shop.ui.screens.product.newproduct.ProductNewScreen
import com.dacs3.shop.ui.screens.product.saleproduct.ProductSaleScreen
import com.dacs3.shop.ui.screens.product.search.SearchDetailsScreen
import com.dacs3.shop.ui.screens.register.SignUpScreen
import com.dacs3.shop.ui.screens.search.SearchScreen

@Composable
fun NavGraphContainer(paddingValues: PaddingValues, navController: NavHostController, bottomBarState: MutableState<Boolean>) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(
            route = "home"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
            HomeScreen(navController)
        }
        composable(
            route = "notification"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
            NotificationScreen(navController)
        }
        composable(
            route = "order"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
            OrderScreen(navController = navController)
        }
        composable(
            route = "account"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = true
            }
            AccountScreen(navController = navController)
        }
        composable(
            route = "login"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            LoginScreen(navController)
        }
        composable("signup") {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            SignUpScreen(navController)
        }
        composable(
            route = "categories"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            CategoryScreen(navController)
        }
        composable(
            route = "category-details/{categoryId}"
        ) { backStackEntry ->
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            CategoryDetailsScreen(categoryId = backStackEntry.arguments?.getString("categoryId"), navController)
        }
        composable(
            route = "product-details/{productId}"
        ) { backStackEntry ->
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            ProductDetailsScreen(productId = backStackEntry.arguments?.getString("productId"), navController)
        }
        composable(
            route = "product-management"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            ProductManageScreen(navHostController = navController)
        }
        composable(
            route = "product-sale"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            ProductSaleScreen(navController = navController)
        }
        composable(
            route = "product-new"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            ProductNewScreen(navController = navController)
        }
        composable(
            route = "create-product",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            CreateProductScreen(navController = navController)
        }
        composable(
            route = "cart"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            CartScreen(navController = navController)
        }
        composable(
            route = "search"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            SearchScreen(navController = navController)
        }
        composable(
            route = "search_result/{query}"
        ) {backStackEntry ->
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            SearchDetailsScreen(query = backStackEntry.arguments?.getString("query"), navController = navController)
        }
        composable(
            route = "category-management"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            CategoryManageScreen(navController = navController)
        }
        composable(
            route = "category-update/{categoryId}"
        ) {backStackEntry ->
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            CategoryUpdateScreen(categoryId = backStackEntry.arguments?.getString("categoryId"), navController = navController)
        }
        composable(
            route = "address"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            AddressManagementScreen(navController = navController)
        }
        composable(
            route = "edit-address/{addressId}"
        ) {backStackEntry ->
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            AddressUpdateScreen(
                addressId = backStackEntry.arguments?.getString("addressId"),
                navController = navController
            )
        }
        composable(
            route = "create-address"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            AddressCreateScreen(navController = navController)
        }
        composable(
            route = "checkout"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            CheckoutScreen(navController = navController)
        }
        composable(
            route = "order-success"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            OrderSuccessScreen(navController = navController)
        }
        composable(
            route = "order-details/{orderId}"
        ) { backStackEntry ->
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            OrderDetailsScreen(
                orderId = backStackEntry.arguments?.getString("orderId"),
                navController = navController
            )
        }
        composable(
            route = "order-item/{orderId}"
        ) { backStackEntry ->
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            OrderItemScreen(
                orderId = backStackEntry.arguments?.getString("orderId"),
                navController = navController
            )
        }
        composable(
            route = "order-management"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            OrderMangerScreen(navController = navController)
        }
        composable(
            route = "create-category"
        ) {
            LaunchedEffect(Unit) {
                bottomBarState.value = false
            }
            CreateCategoryScreen(navController = navController)
        }
    }
}