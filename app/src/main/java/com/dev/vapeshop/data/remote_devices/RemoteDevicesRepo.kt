package com.dev.vapeshop.data.remote_devices

import com.dev.vapeshop.base.SingleLiveEvent
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.Device

interface RemoteDevicesRepo {
    val devicesLiveEvent: SingleLiveEvent<AsyncState<List<Device>>>

    suspend fun getDevices()
}