package com.example.dentapp.data.remote.model.response.clinic

import com.example.dentapp.domain.model.clinic.Clinic
import com.example.dentapp.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID


@Serializable
data class ClinicResponse (
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val logo: String,
    val orgName: String,
    val shortName: String,
    val contacts: String,
    val address: String,
    val email: String
)

fun ClinicResponse.toDomain(): Clinic = Clinic(
    id = id,
    logo = logo,
    orgName = orgName,
    shortName = shortName,
    contacts = contacts,
    address = address,
    email = email
)