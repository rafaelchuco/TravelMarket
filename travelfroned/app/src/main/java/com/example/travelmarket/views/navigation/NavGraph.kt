package com.example.travelmarket.views.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travelmarket.views.ui.activities.list.ActivityListScreen
import com.example.travelmarket.views.ui.auth.AuthContainerScreen
import com.example.travelmarket.views.ui.coupon_list.CouponListScreen
import com.example.travelmarket.views.ui.flights.FlightSearchScreen
import com.example.travelmarket.views.ui.home.HomeScreen
import com.example.travelmarket.views.ui.packages.PackageDetailScreen
import com.example.travelmarket.views.ui.packages.PackageListScreen
import com.example.travelmarket.views.ui.welcome.WelcomeScreen
import com.example.travelmarket.views.ui.activities.detail.ActivityDetailScreen
import com.example.travelmarket.views.ui.auth.EditProfileScreen
import com.example.travelmarket.views.ui.auth.profile.ProfileScreen
import com.example.travelmarket.views.ui.bookings.cancel.CancelBookingScreen
import com.example.travelmarket.views.ui.bookings.create.CreateBookingScreen
import com.example.travelmarket.views.ui.bookings.delete.DeleteBookingScreen
import com.example.travelmarket.views.ui.bookings.detail.BookingDetailScreen
import com.example.travelmarket.views.ui.bookings.list.BookingsListScreen
import com.example.travelmarket.views.ui.bookings.myBookings.MyBookingsScreen
import com.example.travelmarket.views.ui.bookings.update.UpdateBookingScreen
import com.example.travelmarket.views.ui.categories.CategoriesTestScreen
import com.example.travelmarket.views.ui.destinations.list.DestinationListScreen
import com.example.travelmarket.views.ui.flights.FlightsTestScreen
import com.example.travelmarket.views.ui.test.DestinationsTestScreen
import com.example.travelmarket.views.ui.test.HotelsTestScreen
import com.example.travelmarket.views.ui.test.InquiriesTestScreen
import com.example.travelmarket.views.ui.test.PackagesTestScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.Welcome.route,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // --- Tus Pantallas Públicas ---
        composable(Routes.Welcome.route) {
            WelcomeScreen(onStartClick = {
                navController.navigate(Routes.AuthContainer.route) {
                    popUpTo(Routes.Welcome.route) { inclusive = true }
                }
            })
        }

        composable(Routes.AuthContainer.route) {
            AuthContainerScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.AuthContainer.route) { inclusive = true }
                    }
                },
                onRegisterSuccess = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.AuthContainer.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Routes.DestinationList.route) {
            DestinationListScreen(
                onNavigateToPackages = { destinationId -> navController.navigate(Routes.PackageList.createRoute(destinationId)) },
                onNavigateToHome = { navController.navigate(Routes.Home.route) },
                onNavigateToPackageList = { navController.navigate(Routes.PackageList.createRoute("all")) },
                onNavigateToBookings = { navController.navigate(Routes.MyBookings.route) },
                onNavigateToProfile = { navController.navigate(Routes.Profile.route) }
            )
        }

        composable(
            route = Routes.PackageList.routeWithArgs,
            arguments = Routes.PackageList.arguments
        ) { backStackEntry ->
            val destId = backStackEntry.arguments?.getString(Routes.PackageList.ARG_DEST_ID) ?: "all"
            PackageListScreen(
                destinationId = destId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetail = { packageId -> navController.navigate(Routes.PackageDetail.createRoute(packageId)) },
                onNavigateToHome = { navController.navigate(Routes.Home.route) },
                onNavigateToDestinations = { navController.navigate(Routes.DestinationList.route) },
                onNavigateToBookings = { navController.navigate(Routes.MyBookings.route) },
                onNavigateToProfile = { navController.navigate(Routes.Profile.route) }
            )
        }

        composable(
            route = Routes.PackageDetail.routeWithArgs,
            arguments = Routes.PackageDetail.arguments
        ) { backStackEntry ->
            val pkgId = backStackEntry.arguments?.getString(Routes.PackageDetail.ARG_PKG_ID)
            PackageDetailScreen(
                packageId = pkgId,
                onNavigateBack = { navController.popBackStack() },
                onReservarClick = {
                    val packageIdAsInt = 1 // TODO: Necesitamos el ID real como Int
                    navController.navigate(Routes.CreateBooking.createRoute(packageIdAsInt))
                }
            )
        }

        composable(Routes.FlightSearch.route) {
            FlightSearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate(Routes.Home.route) },
                onNavigateToDestinations = { navController.navigate(Routes.DestinationList.route) },
                onNavigateToPackages = { navController.navigate(Routes.PackageList.createRoute("all")) },
                onNavigateToBookings = { navController.navigate(Routes.MyBookings.route) },
                onNavigateToProfile = { navController.navigate(Routes.Profile.route) }
            )
        }

        composable(Routes.CouponList.route) {
            CouponListScreen(onNavigateBack = { navController.popBackStack() })
        }

        // --- Pantallas de Compañeros ---

        composable(Routes.ActivitiesList.route) {
            ActivityListScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToActivityDetail = { activityId ->
                    navController.navigate(Routes.ActivityDetail.createRoute(activityId.toInt()))
                },
                onNavigateToHome = { navController.navigate(Routes.Home.route) },
                onNavigateToDestinations = { navController.navigate(Routes.DestinationList.route) },
                onNavigateToPackages = { navController.navigate(Routes.PackageList.createRoute("all")) },
                onNavigateToBookings = { navController.navigate(Routes.MyBookings.route) },
                onNavigateToProfile = { navController.navigate(Routes.Profile.route) }
            )
        }

        composable(
            route = Routes.ActivityDetail.routeWithArgs,
            arguments = Routes.ActivityDetail.arguments
        ) { backStackEntry ->
            val activityId = backStackEntry.arguments?.getInt(Routes.ActivityDetail.ARG_ACTIVITY_ID) ?: 0
            ActivityDetailScreen(activityId = activityId)
        }

        composable(Routes.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(Routes.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }

        composable(Routes.MyBookings.route) {
            MyBookingsScreen()
        }
        composable(Routes.BookingsList.route) {
            BookingsListScreen()
        }

        composable(
            route = Routes.CreateBooking.routeWithArgs,
            arguments = Routes.CreateBooking.arguments
        ) { backStackEntry ->
            val packageId = backStackEntry.arguments?.getInt(Routes.CreateBooking.ARG_PACKAGE_ID_FOR_BOOKING) ?: 0
            CreateBookingScreen(navController = navController)
        }

        composable(
            route = Routes.BookingDetail.routeWithArgs,
            arguments = Routes.BookingDetail.arguments
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getInt(Routes.BookingDetail.ARG_BOOKING_ID) ?: 0
            BookingDetailScreen(bookingId = bookingId)
        }

        composable(
            route = Routes.UpdateBooking.routeWithArgs,
            arguments = Routes.UpdateBooking.arguments
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getInt(Routes.BookingDetail.ARG_BOOKING_ID) ?: 0
            UpdateBookingScreen(bookingId = bookingId, navController = navController)
        }

        composable(
            route = Routes.DeleteBooking.routeWithArgs,
            arguments = Routes.DeleteBooking.arguments
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getInt(Routes.BookingDetail.ARG_BOOKING_ID) ?: 0
            DeleteBookingScreen(bookingId = bookingId, navController = navController)
        }

        composable(
            route = Routes.CancelBooking.routeWithArgs,
            arguments = Routes.CancelBooking.arguments
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getInt(Routes.BookingDetail.ARG_BOOKING_ID) ?: 0
            CancelBookingScreen(bookingId = bookingId, navController = navController)
        }

        // ========== TEST SCREENS ==========
        composable(Routes.CategoriesTest.route) { CategoriesTestScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.DestinationsTest.route) { DestinationsTestScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.FlightsTest.route) { FlightsTestScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.HotelsTest.route) { HotelsTestScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.InquiriesTest.route) { InquiriesTestScreen(onBack = { navController.popBackStack() }) }
        composable(Routes.PackagesTest.route) { PackagesTestScreen(onBack = { navController.popBackStack() }, navController = navController) }
    }
}
