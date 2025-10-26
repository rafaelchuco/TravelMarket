package com.example.travelmarket.logic.data.remote.auth

import com.example.travelmarket.logic.data.models.request.auth.LoginRequest
import com.example.travelmarket.logic.data.models.request.auth.RefreshTokenRequest
import com.example.travelmarket.logic.data.models.request.auth.RegisterRequest
import com.example.travelmarket.logic.data.models.request.auth.UpdateProfileRequest
import com.example.travelmarket.logic.data.models.response.auth.LoginResponse
import com.example.travelmarket.logic.data.models.response.auth.RefreshTokenResponse
import com.example.travelmarket.logic.data.models.response.auth.RegisterResponse
import com.example.travelmarket.logic.data.models.response.auth.UpdateProfileResponse
import com.example.travelmarket.logic.data.models.response.auth.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApiService {

    @POST("auth/register/")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>  // ✅ BIEN

    @POST("auth/login/")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("auth/users/me/")
    suspend fun getUserProfile(): Response<UserResponse>

    @PUT("auth/users/update_profile/")
    suspend fun updateProfilePut(
        @Body request: UpdateProfileRequest
    ): Response<UserResponse>

    @PATCH("auth/users/update_profile/")
    suspend fun updateProfilePatch(
        @Body request: UpdateProfileRequest
    ): Response<UpdateProfileResponse>  // ✅ BIEN

    @POST("auth/token/refresh/")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<RefreshTokenResponse>
}