package com.example.travelmarket.views.navigation

sealed class Routes(val route: String) {
    // ========== AUTH & BIENVENIDA ==========
    object Welcome : Routes("welcome")  // ✅ NUEVA RUTA
    object Home : Routes("home")
    object Login : Routes("login")
    object Register : Routes("register")
    object Profile : Routes("profile")
    object EditProfile : Routes("edit_profile")

    // ========== ACTIVITIES (Tu amigo) ==========
    object ActivitiesList : Routes("activities_list")
    object ActivityDetail : Routes("activity_detail/{activityId}") {
        fun createRoute(activityId: Int) = "activity_detail/$activityId"
    }

    // ========== TUS MÓDULOS ==========
    object CategoriesTest : Routes("categories_test")
    object DestinationsTest : Routes("destinations_test")
    object FlightsTest : Routes("flights_test")
    object HotelsTest : Routes("hotels_test")
    object InquiriesTest : Routes("inquiries_test")
    object PackagesTest : Routes("packages_test")

    // ========== BOOKINGS (Tu amigo) ==========
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

    // ========== REVIEWS (Alex) ==========
    object ReviewsList : Routes("reviews_list")
    object CreateReview : Routes("create_review")
    object MyReviews : Routes("my_reviews")
    object ReviewDetail : Routes("review_detail/{reviewId}") {
        fun createRoute(reviewId: Int) = "review_detail/$reviewId"
    }
    object UpdateReview : Routes("update_review/{reviewId}") {
        fun createRoute(reviewId: Int) = "update_review/$reviewId"
    }

    // ========== PROMOTIONS (Alex) ==========
    object PromotionsList : Routes("promotions_list")
    object PromotionDetail : Routes("promotion_detail/{promotionId}") {
        fun createRoute(promotionId: Int) = "promotion_detail/$promotionId"
    }
}
