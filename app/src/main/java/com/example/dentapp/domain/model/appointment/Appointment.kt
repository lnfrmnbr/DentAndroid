package com.example.dentapp.domain.model.appointment

import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.presentation.periochart.utils.Tooth
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

data class Appointment(
    val patient: Patient?,
    val date: LocalDateTime = LocalDateTime.now(),
    val tags: List<String>? = emptyList(),
    val indexes: Indexes = Indexes(),
    val complaints: String? = null,
    val comments: String? = null,
    val plan: String? = null,
    val isFirst: Boolean,
    val diagnosis: List<String>? = emptyList(),
    val chart: List<Tooth>? = null
) : java.io.Serializable

@Serializable
data class Indexes(
    val bop: Double = 0.0,
    val api: Double = 0.0,
    val cpitn: List<Int> = listOf(0, 0, 0, 0, 0, 0),
    val russel: Double = 0.0,
    val ohis: Double = 0.0,
    val kpu: Int = 0
) : java.io.Serializable