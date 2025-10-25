package com.example.travelmarket.logic.viewmodels.inquiries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.inquiries.CreateInquiryRequest
import com.example.travelmarket.logic.domain.models.Inquiry
import com.example.travelmarket.logic.domain.usecases.inquiries.CreateInquiryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateInquiryViewModel @Inject constructor(
    private val createInquiryUseCase: CreateInquiryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CreateInquiryState>(CreateInquiryState.Idle)
    val state: StateFlow<CreateInquiryState> = _state.asStateFlow()

    fun createInquiry(request: CreateInquiryRequest) {
        viewModelScope.launch {
            _state.value = CreateInquiryState.Loading
            when (val result = createInquiryUseCase(request)) {
                is NetworkResult.Success -> {
                    _state.value = CreateInquiryState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    _state.value = CreateInquiryState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun resetState() {
        _state.value = CreateInquiryState.Idle
    }
}

sealed class CreateInquiryState {
    data object Idle : CreateInquiryState()
    data object Loading : CreateInquiryState()
    data class Success(val inquiry: Inquiry) : CreateInquiryState()
    data class Error(val message: String) : CreateInquiryState()
}
