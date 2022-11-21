package com.dev.vapeshop.data.remote_cartridges

import com.dev.vapeshop.base.SingleLiveEvent
import com.dev.vapeshop.base.mapToObject
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.CartridgeVaporizer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteCartridgesRepoImpl @Inject constructor(private val db: FirebaseFirestore) :
    RemoteCartridgesRepo {

    override val cartridgesLiveEvent: SingleLiveEvent<AsyncState<List<CartridgeVaporizer>>>
        get() = mutableState

    private val mutableState = SingleLiveEvent<AsyncState<List<CartridgeVaporizer>>>()

    override suspend fun getCartridges(): Unit = withContext(Dispatchers.IO) {
        mutableState.postValue(AsyncState.Loading)
        val cartridges = mutableListOf<CartridgeVaporizer>()

        try {
            db.collection("cartridges_vaporizer")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        try {
                            cartridges.add(mapToObject(document.data, CartridgeVaporizer::class))
                        } catch (e: Exception) {
                            mutableState.postValue(AsyncState.Error("Ошибка получения данных"))
                            return@addOnSuccessListener
                        }
                    }

                    mutableState.postValue(AsyncState.Loaded(cartridges))
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