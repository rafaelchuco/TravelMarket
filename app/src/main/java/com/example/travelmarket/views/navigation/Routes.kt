package com.example.travelmarket.views.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Login : Routes("login")
    object Register : Routes("register")
    object Profile : Routes("profile")
    object EditProfile : Routes("edit_profile")
    object ActivitiesList : Routes("activities_list")
    object ActivityDetail : Routes("activity_detail/{activityId}") {
        fun createRoute(activityId: Int) = "activity_detail/$activityId"
    }
    object DestinationsList : Routes("destinations_list")
    object BookingsList : Routes("bookings_list")

    object CreateBooking : Routes("create_booking/{packageId}") {
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
    object PromotionsList : Routes("promotions_list")
    object PromotionDetail : Routes("promotion_detail/{promotionId}") {
        fun createRoute(promotionId: Int) = "promotion_detail/$promotionId"
    }

    // Reviews Routes
    object ReviewsList : Routes("reviews_list")
    object ReviewDetail : Routes("review_detail/{reviewId}") {
        fun createRoute(reviewId: Int) = "review_detail/$reviewId"
    }
    object CreateReview : Routes("create_review")
    object UpdateReview : Routes("update_review/{reviewId}") {
        fun createRoute(reviewId: Int) = "update_review/$reviewId"
    }
    object MyReviews : Routes("my_reviews")
}