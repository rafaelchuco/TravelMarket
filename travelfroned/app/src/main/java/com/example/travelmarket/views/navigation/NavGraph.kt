package com.example.travelmarket.views.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.travelmarket.views.ui.activities.detail.ActivityDetailScreen
import com.example.travelmarket.views.ui.activities.list.ActivitiesListScreen
import com.example.travelmarket.views.ui.auth.EditProfileScreen
import com.example.travelmarket.views.ui.auth.login.LoginScreen
import com.example.travelmarket.views.ui.auth.profile.ProfileScreen
import com.example.travelmarket.views.ui.auth.register.RegisterScreen
import com.example.travelmarket.views.ui.bookings.cancel.CancelBookingScreen
import com.example.travelmarket.views.ui.bookings.create.CreateBookingScreen
import com.example.travelmarket.views.ui.bookings.delete.DeleteBookingScreen
import com.example.travelmarket.views.ui.bookings.detail.BookingDetailScreen
import com.example.travelmarket.views.ui.bookings.list.BookingsListScreen
import com.example.travelmarket.views.ui.bookings.myBookings.MyBookingsScreen
import com.example.travelmarket.views.ui.bookings.update.UpdateBookingScreen
import com.example.travelmarket.views.ui.categories.CategoriesTestScreen
import com.example.travelmarket.views.ui.home.HomeScreen
import com.example.travelmarket.views.ui.test.DestinationsTestScreen
import com.example.travelmarket.views.ui.test.FlightsTestScreen
import com.example.travelmarket.views.ui.test.HotelsTestScreen
import com.example.travelmarket.views.ui.test.InquiriesTestScreen
import com.example.travelmarket.views.ui.test.PackagesTestScreen
import com.example.travelmarket.views.ui.destinations.list.DestinationListScreen
import com.example.travelmarket.views.ui.packages.PackageListScreen
import com.example.travelmarket.views.ui.packages.PackageDetailScreen
import com.example.travelmarket.views.ui.coupon_list.CouponListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Routes.Home.route,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Routes.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Routes.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Routes.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(Routes.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(Routes.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }

        // ========== MAIN SCREENS ==========
        composable(Routes.DestinationsList.route) {
            DestinationListScreen(
                onNavigateToPackages = { destinationId ->
                    navController.navigate(Routes.PackagesList.route)
                },
                onNavigateToHome = { navController.navigate(Routes.Home.route) },
                onNavigateToPackageList = { navController.navigate(Routes.PackagesList.route) },
                onNavigateToBookings = { navController.navigate(Routes.BookingsList.route) },
                onNavigateToProfile = { navController.navigate(Routes.Profile.route) }
            )
        }

        composable(Routes.PackagesList.route) {
            PackageListScreen(
                destinationId = null,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetail = { packageId ->
                    navController.navigate(Routes.PackageDetail.createRoute(packageId))
                },
                onNavigateToHome = { navController.navigate(Routes.Home.route) },
                onNavigateToDestinations = { navController.navigate(Routes.DestinationsList.route) },
                onNavigateToBookings = { navController.navigate(Routes.BookingsList.route) },
                onNavigateToProfile = { navController.navigate(Routes.Profile.route) }
            )
        }

        composable(
            route = Routes.PackageDetail.route,
            arguments = listOf(
                navArgument("packageId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val packageId = backStackEntry.arguments?.getString("packageId") ?: ""
            PackageDetailScreen(
                packageId = packageId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToBooking = { packageId ->
                    navController.navigate(Routes.CreateBooking.createRoute(packageId.toInt()))
                }
            )
        }

        composable(Routes.FlightsSearch.route) {
            FlightSearchScreenNew(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.CouponsList.route) {
            CouponListScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.ActivitiesList.route) {
            ActivitiesListScreen(navController = navController)
        }

        composable(
            route = Routes.ActivityDetail.route,
            arguments = listOf(
                navArgument("activityId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val activityId = backStackEntry.arguments?.getLong("activityId") ?: 0L
            ActivityDetailScreen(activityId = activityId)
        }

        // ========== TUS TEST SCREENS ==========
        composable(Routes.CategoriesTest.route) {
            CategoriesTestScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.DestinationsTest.route) {
            DestinationsTestScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.FlightsTest.route) {
            FlightsTestScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.HotelsTest.route) {
            HotelsTestScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.InquiriesTest.route) {
            InquiriesTestScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.PackagesTest.route) {
            PackagesTestScreen(
                onBack = { navController.popBackStack() },
                navController = navController  // ✅ ÚNICA LÍNEA AGREGADA
            )
        }

        // ========== BOOKINGS (Tu amigo) ==========
        composable(Routes.BookingsList.route) {
            BookingsListScreen()
        }

        composable(Routes.CreateBooking.route) {
            CreateBookingScreen(navController = navController)
        }

        composable(Routes.MyBookings.route) {
            MyBookingsScreen()
        }

        composable(
            route = Routes.BookingDetail.route,
            arguments = listOf(
                navArgument("bookingId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: 0
            BookingDetailScreen(bookingId = bookingId)
        }

        composable(
            route = Routes.UpdateBooking.route,
            arguments = listOf(
                navArgument("bookingId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: 0
            UpdateBookingScreen(bookingId = bookingId, navController = navController)
        }

        composable(
            route = Routes.DeleteBooking.route,
            arguments = listOf(
                navArgument("bookingId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: 0
            DeleteBookingScreen(bookingId = bookingId, navController = navController)
        }

        composable(
            route = Routes.CancelBooking.route,
            arguments = listOf(
                navArgument("bookingId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: 0
            CancelBookingScreen(bookingId = bookingId, navController = navController)
        }
    }
}
