package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.auth.LoginResponse
import com.example.travelmarket.logic.data.models.response.auth.UserResponse
import com.example.travelmarket.logic.domain.models.User

object AuthMapper {

    fun userResponseToDomain(response: UserResponse): User {
        return User(
            id = response.id,
            username = response.username,
            email = response.email,
            firstName = response.firstName,
            lastName = response.lastName,
            phone = response.phone,
            nationality = response.nationality,
            passportNumber = response.passportNumber,
            address = response.address,
            city = response.city,
            country = response.country,
            userType = response.userType,
            isActive = response.isActive,
            isStaff = response.isStaff,
            isSuperuser = response.isSuperuser,
            lastLogin = response.lastLogin,
            dateJoined = response.dateJoined,
            createdAt = response.createdAt,
            updatedAt = response.updatedAt
        )
    }

    fun loginResponseToUser(response: LoginResponse): User? {
        return response.user?.let { userResponseToDomain(it) }
    }
}