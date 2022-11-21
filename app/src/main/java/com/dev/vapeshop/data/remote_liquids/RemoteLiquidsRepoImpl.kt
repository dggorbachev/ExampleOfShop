package com.dev.vapeshop.data.remote_liquids

import com.dev.vapeshop.base.SingleLiveEvent
import com.dev.vapeshop.base.mapToObject
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.Liquid
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteLiquidsRepoImpl @Inject constructor(private val db: FirebaseFirestore) :
    RemoteLiquidsRepo {

    override val liquidsLiveEvent: SingleLiveEvent<AsyncState<List<Liquid>>>
        get() = mutableState

    private val mutableState = SingleLiveEvent<AsyncState<List<Liquid>>>()

    override suspend fun getLiquids(): Unit = withContext(Dispatchers.IO) {
        mutableState.postValue(AsyncState.Loading)
        val liquids = mutableListOf<Liquid>()

        try {
            db.collection("liquids")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        try {
                            liquids.add(mapToObject(document.data, Liquid::class))
                        } catch (e: Exception) {
                            mutableState.postValue(AsyncState.Error("Ошибка получения данных"))
                            return@addOnSuccessListener
                        }
                    }

                    mutableState.postValue(AsyncState.Loaded(liquids))
                }
                .addOnCanceledListener {
                    mutableState.postValue(AsyncState.Error("Операция отменена"))
                }
                .addOnFailureListener {
                    mutableState.postValue(AsyncState.Error("Ошибка получения данных"))
                }
        } catch (e: Exception) {
            mutableState.postValue(AsyncState.Error("Ошибка получения данных"))
        }
    }
}