package com.example.travelmarket.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.travelmarket.ui.public.activity_list.ActivityListScreen
import com.example.travelmarket.ui.public.auth.AuthContainerScreen
import com.example.travelmarket.ui.public.coupon_list.CouponListScreen
import com.example.travelmarket.ui.public.destination_list.DestinationListScreen
import com.example.travelmarket.ui.public.flight_search.FlightSearchScreen
import com.example.travelmarket.ui.public.home.HomeScreen
import com.example.travelmarket.ui.public.package_detail.PackageDetailScreen
import com.example.travelmarket.ui.public.package_list.PackageListScreen
import com.example.travelmarket.ui.public.welcome.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {

        composable(route = Routes.WELCOME) {
            WelcomeScreen(
                onStartClick = {
                    navController.navigate(Routes.AUTH_CONTAINER) {
                        popUpTo(route = Routes.WELCOME) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Routes.AUTH_CONTAINER) {
            AuthContainerScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(route = Routes.AUTH_CONTAINER) { inclusive = true }
                    }
                },
                onRegisterSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(route = Routes.AUTH_CONTAINER) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Routes.HOME) {
            HomeScreen(
                onNavigateToDestinations = {
                    navController.navigate(Routes.DESTINATION_LIST)
                },
                onNavigateToPackages = {
                    navController.navigate(Routes.packageListWithDestId("all"))
                },
                onNavigateToBookings = { },
                onNavigateToProfile = { },
                onNavigateToCategoryPackages = { categoryName ->
                    navController.navigate(Routes.packageListWithDestId(categoryName))
                },
                onNavigateToFlights = {
                    navController.navigate(Routes.FLIGHT_SEARCH)
                },
                onNavigateToCoupons = {
                    navController.navigate(Routes.COUPON_LIST)
                },
                onNavigateToDestinationList = {
                    navController.navigate(Routes.DESTINATION_LIST)
                },
                onNavigateToPackageList = {
                    navController.navigate(Routes.packageListWithDestId("all"))
                },
                onNavigateToActivities = {
                    navController.navigate(Routes.ACTIVITY_LIST)
                }
            )
        }

        composable(route = Routes.DESTINATION_LIST) {
            DestinationListScreen(
                onNavigateToPackages = { destinationId ->
                    navController.navigate(Routes.packageListWithDestId(destinationId))
                },
                onNavigateToHome = { navController.navigate(Routes.HOME) },
                onNavigateToPackageList = { navController.navigate(Routes.packageListWithDestId("all")) },
                onNavigateToBookings = { },
                onNavigateToProfile = { }
            )
        }

        composable(
            route = "${Routes.PACKAGE_LIST}/{destinationId}",
            arguments = listOf(navArgument("destinationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val destId = backStackEntry.arguments?.getString("destinationId")
            PackageListScreen(
                destinationId = destId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetail = { packageId ->
                    navController.navigate(Routes.packageDetailWithPkgId(packageId))
                },
                onNavigateToHome = { navController.navigate(Routes.HOME) },
                onNavigateToDestinations = { navController.navigate(Routes.DESTINATION_LIST) },
                onNavigateToBookings = { },
                onNavigateToProfile = { }
            )
        }

        composable(
            route = "${Routes.PACKAGE_DETAIL}/{packageId}",
            arguments = listOf(navArgument("packageId") { type = NavType.StringType })
        ) { backStackEntry ->
            val pkgId = backStackEntry.arguments?.getString("packageId")
            PackageDetailScreen(
                packageId = pkgId,
                onNavigateBack = { navController.popBackStack() },
                onReservarClick = { }
            )
        }

        composable(route = Routes.FLIGHT_SEARCH) {
            FlightSearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate(Routes.HOME) },
                onNavigateToDestinations = { navController.navigate(Routes.DESTINATION_LIST) },
                onNavigateToPackages = { navController.navigate(Routes.packageListWithDestId("all")) },
                onNavigateToBookings = { },
                onNavigateToProfile = { }
            )
        }

        composable(route = Routes.COUPON_LIST) {
            CouponListScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = Routes.ACTIVITY_LIST) {
            ActivityListScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToActivityDetail = { activityId ->
                },
                onNavigateToHome = { navController.navigate(Routes.HOME) },
                onNavigateToDestinations = { navController.navigate(Routes.DESTINATION_LIST) },
                onNavigateToPackages = { navController.navigate(Routes.packageListWithDestId("all")) },
                onNavigateToBookings = { },
                onNavigateToProfile = { }
            )
        }
    }
}