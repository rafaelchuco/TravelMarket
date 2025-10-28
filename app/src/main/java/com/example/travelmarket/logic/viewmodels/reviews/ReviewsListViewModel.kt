package com.example.travelmarket.logic.viewmodels.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Review
import com.example.travelmarket.logic.domain.usecases.reviews.GetReviewsParams
import com.example.travelmarket.logic.domain.usecases.reviews.GetReviewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewsListViewModel(
    private val getReviewsUseCase: GetReviewsUseCase
) : ViewModel() {

    private val _reviewsState = MutableStateFlow<NetworkResult<List<Review>>>(NetworkResult.Loading)
    val reviewsState: StateFlow<NetworkResult<List<Review>>> = _reviewsState.asStateFlow()

    init {
        getReviews()
    }

    fun getReviews(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ) {
        viewModelScope.launch {
            _reviewsState.value = NetworkResult.Loading
            _reviewsState.value = getReviewsUseCase(
                GetReviewsParams(
                    search = search,
                    ordering = ordering,
                    page = page
                )
            )
        }
    }

    fun refresh() {
        getReviews()
    }
}