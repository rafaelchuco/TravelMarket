package com.example.travelmarket.logic.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.response.auth.LoginResponse
import com.example.travelmarket.logic.domain.usecases.auth.LoginParams
import com.example.travelmarket.logic.domain.usecases.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<NetworkResult<LoginResponse>?>(null)  // ✅ BIEN
    val loginState: StateFlow<NetworkResult<LoginResponse>?> = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = NetworkResult.Loading
            _loginState.value = loginUseCase(
                LoginParams(username = username, password = password)
            )
        }
    }
}