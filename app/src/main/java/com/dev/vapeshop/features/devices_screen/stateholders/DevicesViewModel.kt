package com.dev.vapeshop.features.devices_screen.stateholders

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.vapeshop.base.SingleLiveEvent
import com.dev.vapeshop.data.remote_devices.RemoteDevicesRepo
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.Device
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class DevicesViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val devicesRepo: RemoteDevicesRepo,
) : ViewModel() {

    val currentDevices: SingleLiveEvent<AsyncState<List<Device>>> = devicesRepo.devicesLiveEvent

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): DevicesViewModel
    }

    fun getDevices() {
        viewModelScope.launch {
            devicesRepo.getDevices()
        }
    }
}