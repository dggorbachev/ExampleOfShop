package com.dev.vapeshop.features.tobacco_screen.stateholders

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.vapeshop.base.SingleLiveEvent
import com.dev.vapeshop.data.remote_tobacco.RemoteTobaccoRepo
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.Tobacco
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class TobaccoViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val tobaccoRepo: RemoteTobaccoRepo,
) : ViewModel() {

    val currentTobacco: SingleLiveEvent<AsyncState<List<Tobacco>>> = tobaccoRepo.tobaccoLiveEvent

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): TobaccoViewModel
    }

    fun getTobacco() {
        viewModelScope.launch {
            tobaccoRepo.getTobacco()
        }
    }
}