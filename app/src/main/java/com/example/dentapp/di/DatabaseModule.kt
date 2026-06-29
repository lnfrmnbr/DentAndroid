package com.example.dentapp.di

import android.content.Context
import com.example.dentapp.data.local.OriScopeDatabase
import com.example.dentapp.data.local.dao.AppointmentDao
import com.example.dentapp.data.local.dao.PatientDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): OriScopeDatabase {
        return OriScopeDatabase.getDatabase(context)
    }

    @Provides
    fun providePatientDao(database: OriScopeDatabase): PatientDao {
        return database.patientDao()
    }

    @Provides
    fun provideAppointmentDao(database: OriScopeDatabase): AppointmentDao {
        return database.appointmentDao()
    }
}