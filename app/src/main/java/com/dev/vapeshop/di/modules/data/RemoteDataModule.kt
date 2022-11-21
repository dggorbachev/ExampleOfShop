package com.dev.vapeshop.di.modules.data

import com.dev.vapeshop.data.remote_cartridges.RemoteCartridgesRepo
import com.dev.vapeshop.data.remote_cartridges.RemoteCartridgesRepoImpl
import com.dev.vapeshop.data.remote_devices.RemoteDevicesRepo
import com.dev.vapeshop.data.remote_devices.RemoteDevicesRepoImpl
import com.dev.vapeshop.data.remote_liquids.RemoteLiquidsRepo
import com.dev.vapeshop.data.remote_liquids.RemoteLiquidsRepoImpl
import com.dev.vapeshop.data.remote_tobacco.RemoteTobaccoRepo
import com.dev.vapeshop.data.remote_tobacco.RemoteTobaccoRepoImpl
import com.dggorbachev.weatherapp.di.annotations.AppScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides

@Module
object RemoteDataModule {

    @Provides
    @AppScope
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @AppScope
    fun provideRemoteCartridgesRepo(db: FirebaseFirestore): RemoteCartridgesRepo =
        RemoteCartridgesRepoImpl(db)

    @Provides
    @AppScope
    fun provideRemoteDevicesRepo(db: FirebaseFirestore): RemoteDevicesRepo =
        RemoteDevicesRepoImpl(db)

    @Provides
    @AppScope
    fun provideRemoteLiquidsRepo(db: FirebaseFirestore): RemoteLiquidsRepo =
        RemoteLiquidsRepoImpl(db)

    @Provides
    @AppScope
    fun provideRemoteTobaccoRepo(db: FirebaseFirestore): RemoteTobaccoRepo =
        RemoteTobaccoRepoImpl(db)
}