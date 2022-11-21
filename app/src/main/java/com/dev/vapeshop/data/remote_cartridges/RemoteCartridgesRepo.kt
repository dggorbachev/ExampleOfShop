package com.dev.vapeshop.data.remote_cartridges

import com.dev.vapeshop.base.SingleLiveEvent
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.CartridgeVaporizer

interface RemoteCartridgesRepo {
    val cartridgesLiveEvent: SingleLiveEvent<AsyncState<List<CartridgeVaporizer>>>

    suspend fun getCartridges()
}