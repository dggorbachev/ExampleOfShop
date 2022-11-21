package com.dev.vapeshop.data.remote_tobacco

import com.dev.vapeshop.base.SingleLiveEvent
import com.dev.vapeshop.base.mapToObject
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.Tobacco
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteTobaccoRepoImpl @Inject constructor(private val db: FirebaseFirestore) :
    RemoteTobaccoRepo {

    override val tobaccoLiveEvent: SingleLiveEvent<AsyncState<List<Tobacco>>>
        get() = mutableState

    private val mutableState = SingleLiveEvent<AsyncState<List<Tobacco>>>()

    override suspend fun getTobacco(): Unit = withContext(Dispatchers.IO) {
        mutableState.postValue(AsyncState.Loading)
        val tobacco = mutableListOf<Tobacco>()

        try {
            db.collection("tobacco")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        try {
                            tobacco.add(mapToObject(document.data, Tobacco::class))
                        } catch (e: Exception) {
                            mutableState.postValue(AsyncState.Error("Ошибка получения данных"))
                            return@addOnSuccessListener
                        }
                    }

                    mutableState.postValue(AsyncState.Loaded(tobacco))
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