package com.example.dentapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dentapp.data.local.entity.AppointmentEntity
import com.example.dentapp.data.local.entity.AppointmentWithPatientName
import java.util.UUID

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: AppointmentEntity)

    @Query("""
        SELECT appointments.*, patients.fullName AS patientFullName 
        FROM appointments 
        INNER JOIN patients ON appointments.patientId = patients.id 
        ORDER BY appointments.date DESC
    """)
    suspend fun getAllAppointments(): List<AppointmentWithPatientName>


    @Query("SELECT * FROM appointments WHERE id = :id")
    suspend fun getAppointmentById(id: UUID): AppointmentEntity?
}