package com.example.travelmarket.logic.viewmodels.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Review
import com.example.travelmarket.logic.domain.usecases.reviews.UpdateReviewParams
import com.example.travelmarket.logic.domain.usecases.reviews.UpdateReviewUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateReviewViewModel(
    private val updateReviewUseCase: UpdateReviewUseCase
) : ViewModel() {

    private val _updateReviewState = MutableStateFlow<NetworkResult<Review>?>(null)
    val updateReviewState: StateFlow<NetworkResult<Review>?> = _updateReviewState.asStateFlow()

    fun updateReview(
        id: Int,
        overallRating: Int?,
        accommodationRating: Int?,
        transportRating: Int?,
        guideRating: Int?,
        valueRating: Int?,
        title: String?,
        comment: String?,
        pros: String?,
        cons: String?
    ) {
        viewModelScope.launch {
            _updateReviewState.value = NetworkResult.Loading
            _updateReviewState.value = updateReviewUseCase(
                UpdateReviewParams(
                    id = id,
                    overallRating = overallRating,
                    accommodationRating = accommodationRating,
                    transportRating = transportRating,
                    guideRating = guideRating,
                    valueRating = valueRating,
                    title = title,
                    comment = comment,
                    pros = pros,
                    cons = cons
                )
            )
        }
    }

    fun resetUpdateState() {
        _updateReviewState.value = null
    }
}