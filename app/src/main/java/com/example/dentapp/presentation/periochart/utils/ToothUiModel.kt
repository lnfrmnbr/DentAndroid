package com.example.dentapp.presentation.periochart.utils

import androidx.annotation.RawRes

data class ToothUiModel(
    val id: Int,
    @RawRes val svgRes: Int,
    val isImplant: Boolean = false,
    val mobility: Int? = null,
    val oral: ToothParams = ToothParams(),
    val vestibular: ToothParams = ToothParams(),
    val removed: Boolean = false,
    val hasSeal: Boolean = false
)

data class ToothParams(
    val bleeding: BoolPoints = BoolPoints(),
    val stones: BoolPoints = BoolPoints(),
    val plaque: BoolPoints = BoolPoints(),
    val pus: BoolPoints = BoolPoints(),
    val furcation: Furcation? = null,
    val caries: Caries = Caries(),
    val probingDepth: IntPoints = IntPoints(),
    val gingivalMargin: IntPoints = IntPoints()
)

data class Caries(
    val c11: Boolean = false,
    val c12: Boolean = false,
    val c21: Boolean = false,
    val c22: Boolean = false,
    val c31: Boolean = false,
    val c32: Boolean = false
    )

data class BoolPoints(
    val p1: Boolean = false,
    val p2: Boolean = false,
    val p3: Boolean = false
)

data class IntPoints(
    val p1: Int? = null,
    val p2: Int? = null,
    val p3: Int? = null
)

data class Furcation(
    val root1: Int? = null,
    val root2: Int? = null
)