package com.example.dentapp.data.remote.model.response.doctor

import fairies.pixels.curlyLabAndroid.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID


@Serializable
data class DoctorResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val fullName: String? = null,
    val firstName: String? = null,
    val sex: String? = null,
)