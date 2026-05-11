package com.example.dentapp.domain.model.clinic

import java.util.UUID

data class Clinic (
    val id: UUID? = null,
    val logo: String,
    val orgName: String,
    val shortName: String,
    val contacts: String,
    val address: String,
    val email: String? = null
)