package com.dev.vapeshop.data.remote_liquids

import com.dev.vapeshop.base.SingleLiveEvent
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.Liquid

interface RemoteLiquidsRepo {
    val liquidsLiveEvent: SingleLiveEvent<AsyncState<List<Liquid>>>

    suspend fun getLiquids()
}