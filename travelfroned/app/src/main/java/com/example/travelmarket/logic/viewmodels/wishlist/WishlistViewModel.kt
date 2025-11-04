package com.example.travelmarket.logic.viewmodels.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.WishlistItem
import com.example.travelmarket.logic.domain.usecases.wishlist.AddToWishlistUseCase
import com.example.travelmarket.logic.domain.usecases.wishlist.GetWishlistUseCase
import com.example.travelmarket.logic.domain.usecases.wishlist.RemoveFromWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val getWishlistUseCase: GetWishlistUseCase,
    private val addToWishlistUseCase: AddToWishlistUseCase,
    private val removeFromWishlistUseCase: RemoveFromWishlistUseCase
) : ViewModel() {

    private val _wishlistState = MutableStateFlow<NetworkResult<List<WishlistItem>>>(NetworkResult.Loading)
    val wishlistState: StateFlow<NetworkResult<List<WishlistItem>>> = _wishlistState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadWishlist() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _wishlistState.value = getWishlistUseCase()
            _isLoading.value = false
        }
    }

    fun addToWishlist(packageId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            when (val result = addToWishlistUseCase(packageId)) {
                is NetworkResult.Success -> {
                    // Recargar la lista después de agregar
                    loadWishlist()
                }
                is NetworkResult.Error -> {
                    _error.value = result.message as String?
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }

    fun removeFromWishlist(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            when (val result = removeFromWishlistUseCase(id)) {
                is NetworkResult.Success -> {
                    // Recargar la lista después de eliminar
                    loadWishlist()
                }
                is NetworkResult.Error -> {
                    _error.value = result.message as String?
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }
}
