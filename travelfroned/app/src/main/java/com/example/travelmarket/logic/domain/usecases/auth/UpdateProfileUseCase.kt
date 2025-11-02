package com.example.travelmarket.logic.domain.usecases.auth

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.User
import com.example.travelmarket.logic.domain.repositories.AuthRepository

data class UpdateProfileParams(
    val firstName: String?,
    val lastName: String?,
    val phone: String?,
    val nationality: String?,
    val passportNumber: String?,
    val address: String?,
    val city: String?,
    val country: String?
)

class UpdateProfileUseCase(
    private val repository: AuthRepository
) : BaseUseCaseWithParams<UpdateProfileParams, NetworkResult<User>>() {

    override suspend fun invoke(params: UpdateProfileParams): NetworkResult<User> {
        return repository.updateProfile(
            firstName = params.firstName,
            lastName = params.lastName,
            phone = params.phone,
            nationality = params.nationality,
            passportNumber = params.passportNumber,
            address = params.address,
            city = params.city,
            country = params.country
        )
    }
}