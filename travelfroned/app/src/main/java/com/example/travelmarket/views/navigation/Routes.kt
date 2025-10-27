package com.example.travelmarket.views.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Routes(val route: String) {

    object Welcome : Routes("welcome")
    object AuthContainer : Routes("auth_container")
    object Home : Routes("home")
    object DestinationList : Routes("destination_list")
    object CouponList : Routes("coupon_list")
    object FlightSearch : Routes("flight_search")

    object PackageList : Routes("package_list/{destinationId}") {
        const val ARG_DEST_ID = "destinationId"
        val routeWithArgs = "package_list/{$ARG_DEST_ID}"
        val arguments = listOf(navArgument(ARG_DEST_ID) { type = NavType.StringType })
        fun createRoute(destinationId: String) = "package_list/$destinationId"
    }

    object PackageDetail : Routes("package_detail/{packageId}") {
        const val ARG_PKG_ID = "packageId"
        val routeWithArgs = "package_detail/{$ARG_PKG_ID}"
        val arguments = listOf(navArgument(ARG_PKG_ID) { type = NavType.StringType })
        fun createRoute(packageId: String) = "package_detail/$packageId"
    }

    object ActivitiesList : Routes("activities_list")
    object ActivityDetail : Routes("activity_detail/{activityId}") {
        const val ARG_ACTIVITY_ID = "activityId"
        val routeWithArgs = "activity_detail/{$ARG_ACTIVITY_ID}"
        val arguments = listOf(navArgument(ARG_ACTIVITY_ID) { type = NavType.IntType })
        fun createRoute(activityId: Int) = "activity_detail/$activityId"
    }


    object Profile : Routes("profile")
    object EditProfile : Routes("edit_profile")

    object BookingsList : Routes("bookings_list")
    object MyBookings : Routes("my_bookings")

    object BookingDetail : Routes("booking_detail/{bookingId}") {
        const val ARG_BOOKING_ID = "bookingId"
        val routeWithArgs = "booking_detail/{$ARG_BOOKING_ID}"
        val arguments = listOf(navArgument(ARG_BOOKING_ID) { type = NavType.IntType })
        fun createRoute(bookingId: Int) = "booking_detail/$bookingId"
    }

    object CreateBooking : Routes("create_booking/{packageId}") {
        const val ARG_PACKAGE_ID_FOR_BOOKING = "packageId"
        val routeWithArgs = "create_booking/{$ARG_PACKAGE_ID_FOR_BOOKING}"
        val arguments = listOf(navArgument(ARG_PACKAGE_ID_FOR_BOOKING) { type = NavType.IntType })
        fun createRoute(packageId: Int) = "create_booking/$packageId"
    }

    object UpdateBooking : Routes("update_booking/{bookingId}") {
        val routeWithArgs = "update_booking/{${BookingDetail.ARG_BOOKING_ID}}"
        val arguments = BookingDetail.arguments
        fun createRoute(bookingId: Int) = "update_booking/$bookingId"
    }

    object DeleteBooking : Routes("delete_booking/{bookingId}") {
        val routeWithArgs = "delete_booking/{${BookingDetail.ARG_BOOKING_ID}}"
        val arguments = BookingDetail.arguments
        fun createRoute(bookingId: Int) = "delete_booking/$bookingId"
    }

    object CancelBooking : Routes("cancel_booking/{bookingId}") {
        val routeWithArgs = "cancel_booking/{${BookingDetail.ARG_BOOKING_ID}}"
        val arguments = BookingDetail.arguments
        fun createRoute(bookingId: Int) = "cancel_booking/$bookingId"
    }

    object CategoriesTest : Routes("categories_test")
    object DestinationsTest : Routes("destinations_test")
    object FlightsTest : Routes("flights_test")
    object HotelsTest : Routes("hotels_test")
    object InquiriesTest : Routes("inquiries_test")
    object PackagesTest : Routes("packages_test")
}