package com.dev.vapeshop.data.remote_devices

import com.dev.vapeshop.base.SingleLiveEvent
import com.dev.vapeshop.base.mapToObject
import com.dev.vapeshop.domain.AsyncState
import com.dev.vapeshop.domain.model.Device
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDevicesRepoImpl @Inject constructor(private val db: FirebaseFirestore) :
    RemoteDevicesRepo {

    override val devicesLiveEvent: SingleLiveEvent<AsyncState<List<Device>>>
        get() = mutableState

    private val mutableState = SingleLiveEvent<AsyncState<List<Device>>>()

    override suspend fun getDevices(): Unit = withContext(Dispatchers.IO) {
        mutableState.postValue(AsyncState.Loading)
        val devices = mutableListOf<Device>()

        try {
            db.collection("devices")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        try {
                            devices.add(mapToObject(document.data, Device::class))
                        } catch (e: Exception) {
                            mutableState.postValue(AsyncState.Error("Ошибка получения данных"))
                            return@addOnSuccessListener
                        }
                    }

                    mutableState.postValue(AsyncState.Loaded(devices))
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