package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.base.BaseRepository
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.mappers.AuthMapper
import com.example.travelmarket.logic.data.models.request.auth.LoginRequest
import com.example.travelmarket.logic.data.models.request.auth.RefreshTokenRequest
import com.example.travelmarket.logic.data.models.request.auth.RegisterRequest
import com.example.travelmarket.logic.data.models.request.auth.UpdateProfileRequest
import com.example.travelmarket.logic.data.models.response.auth.LoginResponse
import com.example.travelmarket.logic.data.models.response.auth.RefreshTokenResponse
import com.example.travelmarket.logic.data.remote.auth.AuthApiService
import com.example.travelmarket.logic.domain.models.User
import com.example.travelmarket.logic.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val apiService: AuthApiService
) : BaseRepository(), AuthRepository {

    override suspend fun registerUser(
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
    ): NetworkResult<User> {
        android.util.Log.d("AUTH_REPO_REGISTER", "===== Iniciando registro =====")

        val request = RegisterRequest(
            username = username,
            email = email ?: "",
            password = password,
            passwordConfirm = passwordConfirm,
            firstName = firstName ?: "",
            lastName = lastName ?: "",
            phone = phone,
            nationality = nationality,
            passportNumber = passportNumber,
            address = address,
            city = city,
            country = country
        )

        android.util.Log.d("AUTH_REPO_REGISTER", "Request: $request")

        val result = executeApiCall {
            apiService.registerUser(request)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val registerResponse = result.data  // ✅ RegisterResponse
                val userResponse = registerResponse.user  // ✅ Extraer el user
                val mappedUser = AuthMapper.userResponseToDomain(userResponse)
                NetworkResult.Success(mappedUser)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun login(
        username: String,
        password: String
    ): NetworkResult<LoginResponse> {
        val request = LoginRequest(
            username = username,
            password = password
        )

        val result = executeApiCall {
            apiService.login(request)
        }

        return when (result) {
            is NetworkResult.Success -> {
                NetworkResult.Success(result.data)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun getUserProfile(): NetworkResult<User> {
        val result = executeApiCall {
            apiService.getUserProfile()
        }

        return when (result) {
            is NetworkResult.Success -> {
                // ✅ CAMBIO AQUÍ
                val profileResponse = result.data  // GetUserProfileResponse
                val userResponse = profileResponse.user  // Extraer el user del wrapper
                val mappedUser = AuthMapper.userResponseToDomain(userResponse)
                NetworkResult.Success(mappedUser)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun updateProfile(
        firstName: String?,
        lastName: String?,
        phone: String?,
        nationality: String?,
        passportNumber: String?,
        address: String?,
        city: String?,
        country: String?
    ): NetworkResult<User> {
        android.util.Log.d("AUTH_REPO", "===== updateProfile INICIADO =====")

        val request = UpdateProfileRequest(
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            nationality = nationality,
            passportNumber = passportNumber,
            address = address,
            city = city,
            country = country
        )

        val result = executeApiCall {
            apiService.updateProfilePatch(request)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val updateResponse = result.data  // ✅ UpdateProfileResponse
                val userResponse = updateResponse.user  // ✅ Extraer el user del wrapper
                val mappedUser = AuthMapper.userResponseToDomain(userResponse)
                NetworkResult.Success(mappedUser)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun refreshToken(
        refreshToken: String
    ): NetworkResult<RefreshTokenResponse> {
        val request = RefreshTokenRequest(
            refreshToken = refreshToken
        )

        val result = executeApiCall {
            apiService.refreshToken(request)
        }

        return when (result) {
            is NetworkResult.Success -> {
                NetworkResult.Success(result.data)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }
}