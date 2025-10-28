package com.example.travelmarket.logic.viewmodels.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Review
import com.example.travelmarket.logic.domain.usecases.reviews.CreateReviewParams
import com.example.travelmarket.logic.domain.usecases.reviews.CreateReviewUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateReviewViewModel(
    private val createReviewUseCase: CreateReviewUseCase
) : ViewModel() {

    private val _createReviewState = MutableStateFlow<NetworkResult<Review>?>(null)
    val createReviewState: StateFlow<NetworkResult<Review>?> = _createReviewState.asStateFlow()

    fun createReview(
        overallRating: Int,
        accommodationRating: Int?,
        transportRating: Int?,
        guideRating: Int?,
        valueRating: Int?,
        title: String,
        comment: String,
        pros: String?,
        cons: String?,
        bookingId: Int,
        packageId: Int
    ) {
        viewModelScope.launch {
            _createReviewState.value = NetworkResult.Loading
            _createReviewState.value = createReviewUseCase(
                CreateReviewParams(
                    overallRating = overallRating,
                    accommodationRating = accommodationRating,
                    transportRating = transportRating,
                    guideRating = guideRating,
                    valueRating = valueRating,
                    title = title,
                    comment = comment,
                    pros = pros,
                    cons = cons,
                    bookingId = bookingId,
                    packageId = packageId
                )
            )
        }
    }

    fun resetCreateState() {
        _createReviewState.value = null
    }
}