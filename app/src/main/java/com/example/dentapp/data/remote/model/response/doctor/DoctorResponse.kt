package com.example.dentapp.data.remote.model.response.doctor

import com.example.dentapp.utils.serializers.DateSerializer
import com.example.dentapp.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID


@Serializable
data class DoctorResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val lastName: String? = null,
    val firstName: String? = null,
    val patronymic: String? = null,
    val sex: String? = null,
    val role: String? = null,
    @Serializable(with = DateSerializer::class)
    val birthDate: Date? = null
)