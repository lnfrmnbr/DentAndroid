package com.example.dentapp.di

import com.example.dentapp.data.repository.auth.AuthRepositoryImpl
import com.example.dentapp.data.repository.auth.VerificationRepositoryImpl
import com.example.dentapp.data.repository.calendar.AppointmentsRepositoryImpl
import com.example.dentapp.data.repository.calendar.LocalAppointmentsRepositoryImpl
import com.example.dentapp.data.repository.clinic.ClinicRepositoryImpl
import com.example.dentapp.data.repository.doctor.DoctorRepositoryImpl
import com.example.dentapp.data.repository.patients.PatientsRepositoryImpl
import com.example.dentapp.domain.repository.auth.AuthRepository
import com.example.dentapp.domain.repository.auth.VerificationRepository
import com.example.dentapp.domain.repository.calendar.AppointmentsRepository
import com.example.dentapp.domain.repository.calendar.LocalAppointmentsRepository
import com.example.dentapp.domain.repository.clinic.ClinicRepository
import com.example.dentapp.domain.repository.doctor.DoctorRepository
import com.example.dentapp.domain.repository.patients.PatientsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindDoctorRepository(
        impl: DoctorRepositoryImpl
    ): DoctorRepository

    @Binds
    @Singleton
    abstract fun bindAppointmentsRepository(
        impl: AppointmentsRepositoryImpl
    ): AppointmentsRepository

    @Binds
    @Singleton
    abstract fun bindPatientsRepository(
        impl: PatientsRepositoryImpl
    ): PatientsRepository

    @Binds
    @Singleton
    abstract fun bindClinicRepository(
        impl: ClinicRepositoryImpl
    ): ClinicRepository

    @Binds
    @Singleton
    abstract fun bindVerificationRepository(
        impl: VerificationRepositoryImpl
    ): VerificationRepository

    @Binds
    @Singleton
    abstract fun bindLocalAppointmentsRepository(
        impl: LocalAppointmentsRepositoryImpl
    ): LocalAppointmentsRepository
}