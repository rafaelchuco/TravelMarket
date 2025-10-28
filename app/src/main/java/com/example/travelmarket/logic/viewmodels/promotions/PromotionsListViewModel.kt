package com.example.travelmarket.logic.viewmodels.promotions

import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.base.BaseViewModel
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.core.network.PaginatedResponse
import com.example.travelmarket.logic.domain.models.Promotion
import com.example.travelmarket.logic.domain.usecases.promotions.GetPromotionsUseCase
import kotlinx.coroutines.launch

class PromotionsListViewModel(
    private val getPromotionsUseCase: GetPromotionsUseCase
) : BaseViewModel<PaginatedResponse<Promotion>>() {

    fun loadPromotions(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ) {
        viewModelScope.launch {
            _uiState.value = NetworkResult.Loading

            val params = GetPromotionsUseCase.Params(
                search = search,
                ordering = ordering,
                page = page
            )

            _uiState.value = getPromotionsUseCase(params)
        }
    }

    fun refresh() {
        loadPromotions()
    }
}