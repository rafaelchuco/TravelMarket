package com.example.travelmarket.logic.domain.usecases.auth

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.response.auth.RefreshTokenResponse
import com.example.travelmarket.logic.domain.repositories.AuthRepository

data class RefreshTokenParams(
    val refreshToken: String
)

class RefreshTokenUseCase(
    private val repository: AuthRepository
) : BaseUseCaseWithParams<RefreshTokenParams, NetworkResult<RefreshTokenResponse>>() {

    override suspend fun invoke(params: RefreshTokenParams): NetworkResult<RefreshTokenResponse> {
        return repository.refreshToken(params.refreshToken)
    }
}