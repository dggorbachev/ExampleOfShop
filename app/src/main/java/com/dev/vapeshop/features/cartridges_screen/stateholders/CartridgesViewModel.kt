package com.dev.vapeshop.features.cartridges_screen.stateholders

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.vapeshop.base.SingleLiveEvent
import com.dev.vapeshop.data.remote_cartridges.RemoteCartridgesRepo
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.CartridgeVaporizer
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class CartridgesViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val cartridgesRepo: RemoteCartridgesRepo,
) : ViewModel() {

    val currentCartridges: SingleLiveEvent<AsyncState<List<CartridgeVaporizer>>> =
        cartridgesRepo.cartridgesLiveEvent

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): CartridgesViewModel
    }

    fun getCartridges() {
        viewModelScope.launch {
            cartridgesRepo.getCartridges()
        }
    }
}