package com.example.travelmarket.logic.domain.usecases.auth

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.User
import com.example.travelmarket.logic.domain.repositories.AuthRepository

data class RegisterUserParams(
    val username: String,
    val email: String?,
    val password: String,
    val passwordConfirm: String,
    val firstName: String?,
    val lastName: String?,
    val phone: String?,
    val nationality: String?,
    val passportNumber: String?,
    val address: String?,
    val city: String?,
    val country: String?
)

class RegisterUserUseCase(
    private val repository: AuthRepository
) : BaseUseCaseWithParams<RegisterUserParams, NetworkResult<User>>() {

    override suspend fun invoke(params: RegisterUserParams): NetworkResult<User> {
        return repository.registerUser(
            username = params.username,
            email = params.email,
            password = params.password,
            passwordConfirm = params.passwordConfirm,
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