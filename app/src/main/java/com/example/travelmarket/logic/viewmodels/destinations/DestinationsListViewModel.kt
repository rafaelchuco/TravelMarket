package com.example.travelmarket.logic.viewmodels.destinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Destination
import com.example.travelmarket.logic.domain.usecases.destinations.GetDestinationsParams
import com.example.travelmarket.logic.domain.usecases.destinations.GetDestinationsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DestinationsListViewModel(
    private val getDestinationsUseCase: GetDestinationsUseCase
) : ViewModel() {

    private val _destinationsState = MutableStateFlow<NetworkResult<List<Destination>>>(NetworkResult.Loading)
    val destinationsState: StateFlow<NetworkResult<List<Destination>>> = _destinationsState.asStateFlow()

    init {
        getDestinations()
    }

    fun getDestinations(
        country: String? = null,
        continent: String? = null,
        isPopular: Boolean? = null,
        bestSeason: String? = null,
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ) {
        viewModelScope.launch {
            _destinationsState.value = NetworkResult.Loading
            _destinationsState.value = getDestinationsUseCase(
                GetDestinationsParams(
                    country = country,
                    continent = continent,
                    isPopular = isPopular,
                    bestSeason = bestSeason,
                    search = search,
                    ordering = ordering,
                    page = page
                )
            )
        }
    }
}