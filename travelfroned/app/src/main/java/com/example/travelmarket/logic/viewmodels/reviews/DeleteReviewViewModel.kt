package com.example.travelmarket.logic.viewmodels.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.usecases.reviews.DeleteReviewUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeleteReviewViewModel(
    private val deleteReviewUseCase: DeleteReviewUseCase
) : ViewModel() {

    private val _deleteReviewState = MutableStateFlow<NetworkResult<Unit>?>(null)
    val deleteReviewState: StateFlow<NetworkResult<Unit>?> = _deleteReviewState.asStateFlow()

    fun deleteReview(id: Int) {
        viewModelScope.launch {
            _deleteReviewState.value = NetworkResult.Loading
            _deleteReviewState.value = deleteReviewUseCase(id)
        }
    }

    fun resetDeleteState() {
        _deleteReviewState.value = null
    }
}