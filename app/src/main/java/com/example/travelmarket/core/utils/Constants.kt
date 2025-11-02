package com.example.travelmarket.core.utils

object Constants {
    const val BASE_URL = "http://10.0.2.2:8000/api/"
    const val TIMEOUT_SECONDS = 30L
    
    // Authentication
    const val PREF_NAME = "travel_market_prefs"
    const val KEY_AUTH_TOKEN = "auth_token"
    const val KEY_REFRESH_TOKEN = "refresh_token"
    const val KEY_USER_ID = "user_id"
    
    // Headers
    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_CONTENT_TYPE = "Content-Type"
    const val CONTENT_TYPE_JSON = "application/json"
    const val TOKEN_PREFIX = "Bearer "
}

