package com.example.travelmarket.logic.viewmodels.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Review
import com.example.travelmarket.logic.domain.usecases.reviews.GetReviewByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewDetailViewModel(
    private val getReviewByIdUseCase: GetReviewByIdUseCase
) : ViewModel() {

    private val _reviewDetailState = MutableStateFlow<NetworkResult<Review>>(NetworkResult.Loading)
    val reviewDetailState: StateFlow<NetworkResult<Review>> = _reviewDetailState.asStateFlow()

    fun getReviewById(id: Int) {
        viewModelScope.launch {
            _reviewDetailState.value = NetworkResult.Loading
            _reviewDetailState.value = getReviewByIdUseCase(id)
        }
    }
}