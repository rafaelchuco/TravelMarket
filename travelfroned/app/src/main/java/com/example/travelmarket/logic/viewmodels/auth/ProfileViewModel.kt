package com.example.travelmarket.logic.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.User
import com.example.travelmarket.logic.domain.usecases.auth.GetProfileUseCase
import com.example.travelmarket.logic.domain.usecases.auth.UpdateProfileParams
import com.example.travelmarket.logic.domain.usecases.auth.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow<NetworkResult<User>>(NetworkResult.Loading)
    val profileState: StateFlow<NetworkResult<User>> = _profileState.asStateFlow()

    // ✅ CAMBIO 1: Estado inicial null en lugar de Loading
    private val _updateProfileState = MutableStateFlow<NetworkResult<User>?>(null)
    val updateProfileState: StateFlow<NetworkResult<User>?> = _updateProfileState.asStateFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            _profileState.value = NetworkResult.Loading
            _profileState.value = getProfileUseCase()
        }
    }

    fun updateProfile(
        firstName: String?,
        lastName: String?,
        phone: String?,
        nationality: String?,
        passportNumber: String?,
        address: String?,
        city: String?,
        country: String?
    ) {
        android.util.Log.d("PROFILE_VM", "===== updateProfile INICIADO =====")
        android.util.Log.d("PROFILE_VM", "firstName: $firstName")
        android.util.Log.d("PROFILE_VM", "lastName: $lastName")
        android.util.Log.d("PROFILE_VM", "phone: $phone")

        viewModelScope.launch {
            android.util.Log.d("PROFILE_VM", "Cambiando estado a Loading")
            _updateProfileState.value = NetworkResult.Loading

            android.util.Log.d("PROFILE_VM", "Llamando updateProfileUseCase")
            _updateProfileState.value = updateProfileUseCase(
                UpdateProfileParams(
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

            android.util.Log.d("PROFILE_VM", "Estado actualizado: ${_updateProfileState.value}")
        }
    }

    // ✅ CAMBIO 2: Función para resetear el estado de actualización
    fun resetUpdateState() {
        _updateProfileState.value = null
    }
}