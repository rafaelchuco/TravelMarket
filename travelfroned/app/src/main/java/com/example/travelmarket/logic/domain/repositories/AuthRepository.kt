package com.example.travelmarket.logic.domain.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.response.auth.LoginResponse
import com.example.travelmarket.logic.data.models.response.auth.RefreshTokenResponse
import com.example.travelmarket.logic.domain.models.User

interface AuthRepository {

    suspend fun registerUser(
        username: String,
        email: String?,
        password: String,
        passwordConfirm: String,
        firstName: String?,
        lastName: String?,
        phone: String?,
        nationality: String?,
        passportNumber: String?,
        address: String?,
        city: String?,
        country: String?
    ): NetworkResult<User>

    suspend fun login(
        username: String,
        password: String
    ): NetworkResult<LoginResponse>

    suspend fun getUserProfile(): NetworkResult<User>

    suspend fun updateProfile(
        firstName: String?,
        lastName: String?,
        phone: String?,
        nationality: String?,
        passportNumber: String?,
        address: String?,
        city: String?,
        country: String?
    ): NetworkResult<User>

    suspend fun refreshToken(
        refreshToken: String
    ): NetworkResult<RefreshTokenResponse>
}