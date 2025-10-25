package com.example.travelmarket.logic.viewmodels.inquiries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Inquiry
import com.example.travelmarket.logic.domain.usecases.inquiries.GetInquiriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InquiriesListViewModel @Inject constructor(
    private val getInquiriesUseCase: GetInquiriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<InquiriesListState>(InquiriesListState.Loading)
    val state: StateFlow<InquiriesListState> = _state.asStateFlow()

    init {
        loadInquiries()
    }

    fun loadInquiries() {
        viewModelScope.launch {
            _state.value = InquiriesListState.Loading
            when (val result = getInquiriesUseCase()) {
                is NetworkResult.Success -> {
                    _state.value = InquiriesListState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    _state.value = InquiriesListState.Error(result.message ?: "Error desconocido")
                }
                // ✅ NO INCLUIR NetworkResult.Loading
                NetworkResult.Loading -> TODO()
            }
        }
    }
}

sealed class InquiriesListState {
    data object Loading : InquiriesListState()
    data class Success(val inquiries: List<Inquiry>) : InquiriesListState()
    data class Error(val message: String) : InquiriesListState()
}
