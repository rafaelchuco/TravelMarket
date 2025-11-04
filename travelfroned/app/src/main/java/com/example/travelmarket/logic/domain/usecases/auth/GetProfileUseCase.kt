package com.example.travelmarket.logic.domain.usecases.auth

import com.example.travelmarket.core.base.BaseUseCase
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.User
import com.example.travelmarket.logic.domain.repositories.AuthRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: AuthRepository
) : BaseUseCase<NetworkResult<User>>() {

    override suspend fun invoke(): NetworkResult<User> {
        return repository.getUserProfile()
    }
}