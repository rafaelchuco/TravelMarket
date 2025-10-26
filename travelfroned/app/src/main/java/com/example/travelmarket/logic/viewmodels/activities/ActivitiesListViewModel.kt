package com.example.travelmarket.logic.viewmodels.activities

import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.base.BaseViewModel
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.core.network.PaginatedResponse
import com.example.travelmarket.logic.domain.models.Activity
import com.example.travelmarket.logic.domain.usecases.activities.GetActivitiesUseCase
import kotlinx.coroutines.launch

class ActivitiesListViewModel(
    private val getActivitiesUseCase: GetActivitiesUseCase
) : BaseViewModel<PaginatedResponse<Activity>>() {

    fun loadActivities(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ) {
        viewModelScope.launch {
            _uiState.value = NetworkResult.Loading

            val params = GetActivitiesUseCase.Params(
                search = search,
                ordering = ordering,
                page = page
            )

            _uiState.value = getActivitiesUseCase(params)
        }
    }

    fun refresh() {
        loadActivities()
    }

    fun searchActivities(query: String) {
        loadActivities(search = query)
    }

    fun sortActivities(orderBy: String) {
        loadActivities(ordering = orderBy)
    }

    fun loadPage(pageNumber: Int) {
        loadActivities(page = pageNumber)
    }
}