package com.dev.vapeshop.features.liquids_screen.stateholders

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.vapeshop.base.SingleLiveEvent
import com.dev.vapeshop.data.remote_liquids.RemoteLiquidsRepo
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.Liquid
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class LiquidsViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val liquidsRepo: RemoteLiquidsRepo,
) : ViewModel() {

    val currentLiquids: SingleLiveEvent<AsyncState<List<Liquid>>> = liquidsRepo.liquidsLiveEvent

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): LiquidsViewModel
    }

    fun getLiquids() {
        viewModelScope.launch {
            liquidsRepo.getLiquids()
        }
    }
}