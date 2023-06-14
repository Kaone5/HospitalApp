package com.mycompany.hospitalapp2.di

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.mycompany.hospitalapp2.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAppointmentRepository(
        database: FirebaseFirestore
    ): AppointmentRepository {
        return AppointmentRepositoryImp(database)
    }

    @Provides
    @Singleton
    fun provideDoctorRepository(
        database: FirebaseFirestore
    ): DoctorRepository {
        return DoctorRepositoryImp(database)
    }
    @Provides
    @Singleton
    fun provideCallRepository(
        database: FirebaseFirestore
    ): CallRepository {
        return CallRepositoryImp(database)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        appPreferences: SharedPreferences,
        gson: Gson
    ): AuthRepository {
        return AuthRepositoryImp(auth, database, appPreferences, gson)
    }
}