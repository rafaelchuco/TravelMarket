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
import com.example.travelmarket.views.ui.bookings.update.UpdateBookingScreen
import com.example.travelmarket.views.ui.categories.CategoriesTestScreen
import com.example.travelmarket.views.ui.home.HomeScreen
import com.example.travelmarket.views.ui.promotions.PromotionDetailScreen
import com.example.travelmarket.views.ui.promotions.PromotionsListScreen
import com.example.travelmarket.views.ui.reviews.CreateReviewScreen
import com.example.travelmarket.views.ui.reviews.MyReviewsScreen
import com.example.travelmarket.views.ui.reviews.ReviewDetailScreen
import com.example.travelmarket.views.ui.reviews.UpdateReviewScreen
import com.example.travelmarket.views.ui.test.DestinationsTestScreen
import com.example.travelmarket.views.ui.test.FlightsTestScreen
import com.example.travelmarket.views.ui.test.HotelsTestScreen
import com.example.travelmarket.views.ui.test.InquiriesTestScreen
import com.example.travelmarket.views.ui.test.PackagesTestScreen
import com.example.travelmarket.views.ui.welcome.WelcomeScreen  // ✅ IMPORTAR
import com.example.travelmarket.ui.customer.WishlistScreen
import com.example.travelmarket.ui.customer.ReservationsScreen
import com.example.travelmarket.ui.customer.ReservationDetailScreen
import com.example.travelmarket.ui.customer.BookingFlowScreen
import com.example.travelmarket.ui.customer.ChangePasswordScreen
import com.example.travelmarket.ui.customer.MyQueriesScreen
import com.example.travelmarket.ui.customer.NewQueryScreen
import com.example.travelmarket.core.storage.TokenManager

@Composable
fun NavGraph(
    navController: NavHostController,
    tokenManager: TokenManager,
    startDestination: String = Routes.Welcome.route,  // ✅ CAMBIAR A WELCOME
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // ✅ PANTALLA DE BIENVENIDA (PRIMERA PANTALLA)
        composable(Routes.Welcome.route) {
            WelcomeScreen(
                onStartClick = {
                    navController.navigate(Routes.Login.route)
                },
                onRegisterClick = {
                    navController.navigate(Routes.Register.route)
                },
                onSkipClick = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Routes.Login.route) {
            LoginScreen(
                navController = navController,
                tokenManager = tokenManager
            )
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

        composable(Routes.ActivitiesList.route) {
            ActivitiesListScreen(navController = navController)
        }

        composable(
            route = Routes.ActivityDetail.route,
            arguments = listOf(
                navArgument("activityId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val activityId = backStackEntry.arguments?.getInt("activityId") ?: 0
            ActivityDetailScreen(activityId = activityId)
        }

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
                onBack = { navController.popBackStack() },
                navController = navController
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
                navController = navController
            )
        }

        composable(Routes.BookingsList.route) {
            BookingsListScreen(navController = navController)
        }

        composable(Routes.CreateBooking.route) {
            CreateBookingScreen(navController = navController)
        }

        composable(
            route = Routes.BookingDetail.route,
            arguments = listOf(
                navArgument("bookingId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: 0
            BookingDetailScreen(
                bookingId = bookingId,
                navController = navController
            )
        }

        composable(
            route = Routes.UpdateBooking.route,
            arguments = listOf(
                navArgument("bookingId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: 0
            UpdateBookingScreen(
                bookingId = bookingId,
                navController = navController
            )
        }

        composable(
            route = Routes.DeleteBooking.route,
            arguments = listOf(
                navArgument("bookingId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: 0
            DeleteBookingScreen(
                bookingId = bookingId,
                navController = navController
            )
        }

        composable(
            route = Routes.CancelBooking.route,
            arguments = listOf(
                navArgument("bookingId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: 0
            CancelBookingScreen(
                bookingId = bookingId,
                navController = navController
            )
        }

        composable(Routes.CreateReview.route) {
            CreateReviewScreen(navController = navController)
        }

        composable(Routes.MyReviews.route) {
            MyReviewsScreen(navController = navController)
        }

        composable(
            route = Routes.ReviewDetail.route,
            arguments = listOf(
                navArgument("reviewId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val reviewId = backStackEntry.arguments?.getInt("reviewId") ?: 0
            ReviewDetailScreen(reviewId = reviewId)
        }

        composable(
            route = Routes.UpdateReview.route,
            arguments = listOf(
                navArgument("reviewId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val reviewId = backStackEntry.arguments?.getInt("reviewId") ?: 0
            UpdateReviewScreen(
                reviewId = reviewId,
                navController = navController
            )
        }

        composable(Routes.PromotionsList.route) {
            PromotionsListScreen(navController = navController)
        }

        composable(
            route = Routes.PromotionDetail.route,
            arguments = listOf(
                navArgument("promotionId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val promotionId = backStackEntry.arguments?.getInt("promotionId") ?: 0
            PromotionDetailScreen(promotionId = promotionId)
        }

        // ========== CUSTOMER (Deivid) ==========
        composable(Routes.Wishlist.route) {
            WishlistScreen(
                navController = navController,
                onViewDetails = { packageId -> 
                    // TODO: Navegar a detalle de paquete cuando esté disponible
                    // navController.navigate(Routes.PackageDetail.createRoute(packageId.toInt()))
                },
                onBookNow = { packageId -> 
                    navController.navigate(Routes.BookingFlow.route) 
                }
            )
        }

        composable(Routes.MyReservations.route) {
            ReservationsScreen(navController = navController)
        }

        composable(
            route = Routes.ReservationDetail.route,
            arguments = listOf(
                navArgument("reservationId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val reservationId = backStackEntry.arguments?.getLong("reservationId") ?: 0L
            ReservationDetailScreen(
                reservationId = reservationId,
                navController = navController
            )
        }

        composable(
            route = Routes.BookingFlow.route,
            arguments = listOf(
                navArgument("packageId") { 
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )
        ) { backStackEntry ->
            val packageId = backStackEntry.arguments?.getLong("packageId") ?: 0L
            BookingFlowScreen(
                packageId = if (packageId > 0) packageId else null,
                navController = navController
            )
        }
        composable(Routes.ChangePassword.route) { 
            ChangePasswordScreen(navController = navController) 
        }
        composable(Routes.MyQueries.route) { 
            MyQueriesScreen(navController = navController) 
        }
        composable(Routes.NewQuery.route) { 
            NewQueryScreen(navController = navController) 
        }
    }
}
