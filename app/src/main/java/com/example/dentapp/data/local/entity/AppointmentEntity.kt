package com.example.dentapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.presentation.periochart.utils.Tooth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.Instant
import java.time.ZoneId
import java.util.UUID

@Entity(
    tableName = "appointments",
    foreignKeys = [
        ForeignKey(
            entity = PatientEntity::class,
            parentColumns = ["id"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.Companion.SET_NULL
        )
    ]
)
data class AppointmentEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val patientId: UUID? = null,
    val date: Long = System.currentTimeMillis(),
    val tags: String? = null,
    val diagnosis: String? = null,
    val complaints: String? = null,
    val comments: String? = null,
    val plan: String? = null,
    val isFirst: Boolean? = null,
    val indexes: String? = null,
    val chart: String? = null
)

fun AppointmentEntity.toDomain(): Appointment {
    val gson = Gson()

    return Appointment(
        id = id,
        patientId = patientId,
        date = Instant.ofEpochMilli(date)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        tags = tags?.let { json ->
            try {
                val listType = object : TypeToken<List<String>>() {}.type
                gson.fromJson(json, listType)
            } catch (_: Exception) {
                emptyList()
            }
        },
        diagnosis = diagnosis?.let { json ->
            try {
                val listType = object : TypeToken<List<String>>() {}.type
                gson.fromJson(json, listType)
            } catch (_: Exception) {
                emptyList()
            }
        },
        complaints = complaints,
        comments = comments,
        plan = plan,
        isFirst = isFirst,
        indexes = indexes?.let { json ->
            try {
                gson.fromJson(json, Indexes::class.java)
            } catch (_: Exception) {
                null
            }
        },
        chart = chart?.let { json ->
            try {
                val listType = object : TypeToken<List<Tooth>>() {}.type
                gson.fromJson(json, listType)
            } catch (_: Exception) {
                null
            }
        }
    )
}