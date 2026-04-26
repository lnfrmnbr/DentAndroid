package com.example.dentapp.presentation.periochart.utils

import kotlinx.serialization.Serializable

@Serializable
data class Tooth(
    val id: Int,
    val svgRes: Int,
    val isImplant: Boolean = false,
    val mobility: Int? = null,
    val oral: ToothParams = ToothParams(),
    val vestibular: ToothParams = ToothParams(),
    val removed: Boolean = false,
    val hasSeal: Boolean = false,
    val pulpPerio: Int = 0
) : java.io.Serializable

@Serializable
data class ToothParams(
    val bleeding: BoolPoints = BoolPoints(),
    val stones: BoolPoints = BoolPoints(),
    val plaque: BoolPoints = BoolPoints(),
    val pus: BoolPoints = BoolPoints(),
    val furcation: Furcation = Furcation(),
    val caries: Caries = Caries(),
    val probingDepth: IntPoints = IntPoints(),
    val gingivalMargin: IntPoints = IntPoints()
) : java.io.Serializable

@Serializable
data class Caries(
    val c11: Boolean = false,
    val c12: Boolean = false,
    val c13: Boolean = false,
    val c21: Boolean = false,
    val c22: Boolean = false,
    val c23: Boolean = false
) : java.io.Serializable

@Serializable
data class BoolPoints(
    val p1: Boolean = false,
    val p2: Boolean = false,
    val p3: Boolean = false
) : java.io.Serializable

@Serializable
data class IntPoints(
    val p1: Int? = null,
    val p2: Int? = null,
    val p3: Int? = null
) : java.io.Serializable

@Serializable
data class Furcation(
    val root1: Int = 0,
    val root2: Int = 0
) : java.io.Serializable