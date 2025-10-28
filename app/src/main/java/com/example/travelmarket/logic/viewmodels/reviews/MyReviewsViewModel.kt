package com.example.travelmarket.logic.viewmodels.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Review
import com.example.travelmarket.logic.domain.usecases.reviews.GetMyReviewsParams
import com.example.travelmarket.logic.domain.usecases.reviews.GetMyReviewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyReviewsViewModel(
    private val getMyReviewsUseCase: GetMyReviewsUseCase
) : ViewModel() {

    private val _myReviewsState = MutableStateFlow<NetworkResult<List<Review>>>(NetworkResult.Loading)
    val myReviewsState: StateFlow<NetworkResult<List<Review>>> = _myReviewsState.asStateFlow()

    init {
        getMyReviews()
    }

    fun getMyReviews(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ) {
        viewModelScope.launch {
            _myReviewsState.value = NetworkResult.Loading
            _myReviewsState.value = getMyReviewsUseCase(
                GetMyReviewsParams(
                    search = search,
                    ordering = ordering,
                    page = page
                )
            )
        }
    }

    fun refresh() {
        getMyReviews()
    }
}