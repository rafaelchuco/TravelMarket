package com.example.travelmarket.data.remote

import com.example.travelmarket.domain.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

// Modelos para requests/responses
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val access: String,
    val refresh: String,
    val user: User
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phone: String?,
    val city: String?,
    val nationality: String?
)

data class RegisterResponse(
    val user: User,
    val message: String?
)

data class RefreshTokenRequest(
    val refresh: String
)

data class RefreshTokenResponse(
    val access: String
)

interface AuthApiService {
    @POST("auth/login/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    
    @POST("auth/register/")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
    
    @GET("auth/users/me/")
    suspend fun getUserProfile(): Response<User>
    
    @POST("auth/token/refresh/")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>
}

