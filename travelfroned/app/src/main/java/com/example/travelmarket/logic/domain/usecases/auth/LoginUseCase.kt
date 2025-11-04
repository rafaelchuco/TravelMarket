package com.example.travelmarket.logic.domain.usecases.auth

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.response.auth.LoginResponse
import com.example.travelmarket.logic.domain.repositories.AuthRepository
import javax.inject.Inject

data class LoginParams(
    val username: String,
    val password: String
)

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) : BaseUseCaseWithParams<LoginParams, NetworkResult<LoginResponse>>() {

    override suspend fun invoke(params: LoginParams): NetworkResult<LoginResponse> {
        return repository.login(params.username, params.password)
    }
}