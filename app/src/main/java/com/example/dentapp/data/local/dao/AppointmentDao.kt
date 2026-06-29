package com.example.dentapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dentapp.data.local.entity.AppointmentEntity
import java.util.UUID

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: AppointmentEntity)

    @Query("SELECT * FROM appointments ORDER BY date DESC")
    suspend fun getAllAppointments(): List<AppointmentEntity>

    @Query("SELECT * FROM appointments WHERE id = :id")
    suspend fun getAppointmentById(id: UUID): AppointmentEntity?
}