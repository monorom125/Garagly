package com.rom.garagely.appModule

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rom.garagely.common.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFireBase() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun providePreferencesManager(): PreferencesManager = PreferencesManager.instance

    @Provides
    @Singleton
    fun provideFireBaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

}