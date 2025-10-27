package com.example.travelmarket.views.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Login : Routes("login")
    object Register : Routes("register")
    object Profile : Routes("profile")
    object EditProfile : Routes("edit_profile")

    // ========== MAIN SCREENS ==========
    object DestinationsList : Routes("destinations_list")
    object PackagesList : Routes("packages_list")
    object PackageDetail : Routes("package_detail/{packageId}") {
        fun createRoute(packageId: String) = "package_detail/$packageId"
    }
    object FlightsSearch : Routes("flights_search")
    object CouponsList : Routes("coupons_list")
    object ActivitiesList : Routes("activities_list")
    object ActivityDetail : Routes("activity_detail/{activityId}") {
        fun createRoute(activityId: Long) = "activity_detail/$activityId"
    }

    // ========== BOOKINGS ==========
    object BookingsList : Routes("bookings_list")
    object CreateBooking {
        const val route = "create_booking/{packageId}"
        fun createRoute(packageId: Int) = "create_booking/$packageId"
    }
    object MyBookings : Routes("my_bookings")
    object BookingDetail : Routes("booking_detail/{bookingId}") {
        fun createRoute(bookingId: Int) = "booking_detail/$bookingId"
    }
    object UpdateBooking : Routes("update_booking/{bookingId}") {
        fun createRoute(bookingId: Int) = "update_booking/$bookingId"
    }
    object DeleteBooking : Routes("delete_booking/{bookingId}") {
        fun createRoute(bookingId: Int) = "delete_booking/$bookingId"
    }
    object CancelBooking : Routes("cancel_booking/{bookingId}") {
        fun createRoute(bookingId: Int) = "cancel_booking/$bookingId"
    }
}
