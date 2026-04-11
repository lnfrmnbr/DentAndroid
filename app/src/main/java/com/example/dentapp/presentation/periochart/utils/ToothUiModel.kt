package com.example.dentapp.presentation.periochart.utils

import androidx.annotation.RawRes

data class ToothUiModel(
    val id: Int,
    @RawRes val svgRes: Int,
    val hasCaries: Boolean = false,
    val isImplant: Boolean = false
)