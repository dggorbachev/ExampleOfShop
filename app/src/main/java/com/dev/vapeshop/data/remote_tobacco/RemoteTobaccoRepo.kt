package com.dev.vapeshop.data.remote_tobacco

import com.dev.vapeshop.base.SingleLiveEvent
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.Tobacco

interface RemoteTobaccoRepo {
    val tobaccoLiveEvent: SingleLiveEvent<AsyncState<List<Tobacco>>>

    suspend fun getTobacco()
}