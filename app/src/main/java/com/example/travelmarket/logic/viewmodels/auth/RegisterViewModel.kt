package com.example.travelmarket.logic.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.User
import com.example.travelmarket.logic.domain.usecases.auth.RegisterUserParams
import com.example.travelmarket.logic.domain.usecases.auth.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow<NetworkResult<User>?>(null)  // ✅ BIEN
    val registerState: StateFlow<NetworkResult<User>?> = _registerState.asStateFlow()
    fun registerUser(
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
    ) {
        viewModelScope.launch {
            android.util.Log.d("REGISTER_VM", "===== Iniciando registro =====")
            _registerState.value = NetworkResult.Loading

            val result = registerUserUseCase(
                RegisterUserParams(
                    username = username,
                    email = email,
                    password = password,
                    passwordConfirm = passwordConfirm,
                    firstName = firstName,
                    lastName = lastName,
                    phone = phone,
                    nationality = nationality,
                    passportNumber = passportNumber,
                    address = address,
                    city = city,
                    country = country
                )
            )

            android.util.Log.d("REGISTER_VM", "Resultado: $result")
            _registerState.value = result
        }
    }
}