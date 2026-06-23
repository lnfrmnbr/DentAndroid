package com.example.dentapp.presentation.periochart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentapp.R
import com.example.dentapp.domain.model.appointment.Appointment
import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.presentation.periochart.utils.BoolPoints
import com.example.dentapp.presentation.periochart.utils.Caries
import com.example.dentapp.presentation.periochart.utils.Furcation
import com.example.dentapp.presentation.periochart.utils.IntPoints
import com.example.dentapp.presentation.periochart.utils.Tooth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class PerioChartViewModel @Inject constructor() : ViewModel() {
    val upTeethUnit = listOf(
        Tooth(18, R.raw.t18),
        Tooth(17, R.raw.t17),
        Tooth(16, R.raw.t16),
        Tooth(15, R.raw.t15),
        Tooth(14, R.raw.t14),
        Tooth(13, R.raw.t13),
        Tooth(12, R.raw.t12),
        Tooth(11, R.raw.t11),
        Tooth(21, R.raw.t21),
        Tooth(22, R.raw.t22),
        Tooth(23, R.raw.t23),
        Tooth(24, R.raw.t24),
        Tooth(25, R.raw.t25),
        Tooth(26, R.raw.t26),
        Tooth(27, R.raw.t27),
        Tooth(28, R.raw.t28)
    )
    val lowTeethUnit = listOf(
        Tooth(48, R.raw.t48),
        Tooth(47, R.raw.t47),
        Tooth(46, R.raw.t46),
        Tooth(45, R.raw.t45),
        Tooth(44, R.raw.t44),
        Tooth(43, R.raw.t43),
        Tooth(42, R.raw.t42),
        Tooth(41, R.raw.t41),
        Tooth(31, R.raw.t31),
        Tooth(32, R.raw.t32),
        Tooth(33, R.raw.t33),
        Tooth(34, R.raw.t34),
        Tooth(35, R.raw.t35),
        Tooth(36, R.raw.t36),
        Tooth(37, R.raw.t37),
        Tooth(38, R.raw.t38)
    )

    private val _upTeeth = MutableStateFlow(upTeethUnit.map { it.copy() })
    val upTeeth: StateFlow<List<Tooth>> = _upTeeth.asStateFlow()

    private val _lowTeeth = MutableStateFlow(lowTeethUnit.map { it.copy() })
    val lowTeeth: StateFlow<List<Tooth>> = _lowTeeth.asStateFlow()

    private val _selectedTeeth = MutableStateFlow<List<Int>>(emptyList())
    val selectedTeeth: StateFlow<List<Int>> = _selectedTeeth.asStateFlow()

    private val _currentJawType = MutableStateFlow<JawType>(JawType.UPPER)
    val currentJawType: StateFlow<JawType> = _currentJawType.asStateFlow()

    private val _currentSurfaceType = MutableStateFlow<SurfaceType>(SurfaceType.VESTIBULAR)
    val currentSurfaceType: StateFlow<SurfaceType> = _currentSurfaceType.asStateFlow()

    private val _appointment = MutableStateFlow<Appointment?>(null)
    val appointment: StateFlow<Appointment?> = _appointment.asStateFlow()

    private val _complaints = MutableStateFlow("")
    val complaints: StateFlow<String> = _complaints.asStateFlow()

    private val _comments = MutableStateFlow("")
    val comments: StateFlow<String> = _comments.asStateFlow()

    private val _treatmentPlan = MutableStateFlow("")
    val treatmentPlan: StateFlow<String> = _treatmentPlan.asStateFlow()

    private val _diagnosis = MutableStateFlow<List<String>>(emptyList())
    val diagnosis: StateFlow<List<String>> = _diagnosis.asStateFlow()

    private val _bop = MutableStateFlow(0.0)
    val bop: StateFlow<Double> = _bop

    private val _api = MutableStateFlow(0.0)
    val api: StateFlow<Double> = _api

    private val _russel = MutableStateFlow(0.0)
    val russel: StateFlow<Double> = _russel

    private val _ohis = MutableStateFlow(0.0)
    val ohis: StateFlow<Double> = _ohis

    private val _kpu = MutableStateFlow(0)
    val kpu: StateFlow<Int> = _kpu

    private val _cpitn = MutableStateFlow(listOf(0, 0, 0, 0, 0, 0))
    val cpitn: StateFlow<List<Int>> = _cpitn

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    enum class JawType { UPPER, LOWER }
    enum class SurfaceType { VESTIBULAR, ORAL }
    enum class CariesType { C11, C12, C13, C21, C22, C23 }

    init {
        viewModelScope.launch {
            combine(_upTeeth, _lowTeeth) { up, low ->
                up + low
            }.collect { allTeeth ->
                val kpu = allTeeth.count { it.hasCaries() || it.hasSeal || it.removed }
                _kpu.value = kpu
            }
        }
    }

    private fun Tooth.hasCaries(): Boolean {
        val oralCaries = oral.caries
        val vestibularCaries = vestibular.caries

        return listOf(
            oralCaries.c11,
            oralCaries.c12,
            oralCaries.c13,
            oralCaries.c21,
            oralCaries.c22,
            oralCaries.c23,
            vestibularCaries.c11,
            vestibularCaries.c12,
            vestibularCaries.c13,
            vestibularCaries.c21,
            vestibularCaries.c22,
            vestibularCaries.c23
        ).any { it }
    }

    fun selectTooth(toothId: Int) {
        _selectedTeeth.value = _selectedTeeth.value + toothId
    }

    fun clearSelection() {
        _selectedTeeth.value = emptyList()
    }

    fun setAppointment(appointment: Appointment?) {
        _appointment.value = appointment
        appointment?.comments?.let {
            _comments.value = it
        }
        appointment?.complaints?.let {
            _complaints.value = it
        }
    }

    fun deleteDiagnose(diagnose: String) {
        _diagnosis.update { currentList ->
            currentList.filter { it != diagnose }
        }
    }

    fun addDiagnose(diagnose: String) {
        if (diagnose.isBlank()) return

        val trimmedDiagnose = diagnose.trim()

        if (!_diagnosis.value.contains(trimmedDiagnose)) {
            _diagnosis.update { currentList ->
                currentList + trimmedDiagnose
            }
        }
    }

    fun updateTreatmentPlan(value: String) {
        _treatmentPlan.value = value
    }

    fun updateComments(value: String) {
        _comments.value = value
    }

    fun updateComplaints(value: String) {
        _complaints.value = value
    }

    fun getAllDiseases(): List<String> {
        return listOf(
            "Кариес",
            "Пульпит",
            "Периодонтит",
            "Гингивит",
            "Стоматит",
            "Пародонтоз",
            "Пародонтит",
            "Флюороз",
            "Эрозия эмали",
            "Киста зуба",
            "Абсцесс",
            "Альвеолит"
        )
    }

    fun changeJawType(jawType: JawType) {
        _currentJawType.value = jawType
    }

    fun changeSurfaceType(surfaceType: SurfaceType) {
        _currentSurfaceType.value = surfaceType
    }

    fun changeRemoved(toothIds: List<Int>, isRemoved: Boolean) {
        updateTeeth(toothIds) { it.copy(removed = isRemoved) }
    }

    fun changeMobility(toothIds: List<Int>, mobility: Int) {
        updateTeeth(toothIds) { it.copy(mobility = mobility) }
    }

    fun changeIsImplant(toothIds: List<Int>, isImplant: Boolean) {
        updateTeeth(toothIds) { it.copy(isImplant = isImplant) }
    }

    fun changeHasSeal(toothIds: List<Int>, hasSeal: Boolean) {
        updateTeeth(toothIds) { it.copy(hasSeal = hasSeal) }
    }

    fun changePulpPerio(toothIds: List<Int>, pulpPerio: Int) {
        updateTeeth(toothIds) { it.copy(pulpPerio = pulpPerio) }
    }

    fun changeProbingDepth(toothIds: List<Int>, probingDepth: Int, pointNum: Int) {
        updateToothParam(toothIds) { tooth, isVestibular ->
            if (isVestibular) {
                tooth.copy(
                    vestibular = tooth.vestibular.copy(
                        probingDepth = updateIntPoint(tooth.vestibular.probingDepth, pointNum, probingDepth)
                    )
                )
            } else {
                tooth.copy(
                    oral = tooth.oral.copy(
                        probingDepth = updateIntPoint(tooth.oral.probingDepth, pointNum, probingDepth)
                    )
                )
            }
        }
    }

    fun changeGingivalMargin(toothIds: List<Int>, gingivalMargin: Int, pointNum: Int) {
        updateToothParam(toothIds) { tooth, isVestibular ->
            if (isVestibular) {
                tooth.copy(
                    vestibular = tooth.vestibular.copy(
                        gingivalMargin = updateIntPoint(tooth.vestibular.gingivalMargin, pointNum, gingivalMargin)
                    )
                )
            } else {
                tooth.copy(
                    oral = tooth.oral.copy(
                        gingivalMargin = updateIntPoint(tooth.oral.gingivalMargin, pointNum, gingivalMargin)
                    )
                )
            }
        }
    }

    fun changeFurcation(toothIds: List<Int>, furcationValue: Int, rootNum: Int) {
        updateToothParam(toothIds) { tooth, isVestibular ->
            if (isVestibular) {
                tooth.copy(
                    vestibular = tooth.vestibular.copy(
                        furcation = updateFurcation(tooth.vestibular.furcation, furcationValue, rootNum)
                    )
                )
            } else {
                tooth.copy(
                    oral = tooth.oral.copy(
                        furcation = updateFurcation(tooth.oral.furcation, furcationValue, rootNum)
                    )
                )
            }
        }
    }

    fun changeBleeding(toothIds: List<Int>, isBleeding: Boolean, pointNum: Int) {
        updateToothParam(toothIds) { tooth, isVestibular ->
            if (isVestibular) {
                tooth.copy(
                    vestibular = tooth.vestibular.copy(
                        bleeding = updateBoolPoint(tooth.vestibular.bleeding, pointNum, isBleeding)
                    )
                )
            } else {
                tooth.copy(
                    oral = tooth.oral.copy(
                        bleeding = updateBoolPoint(tooth.oral.bleeding, pointNum, isBleeding)
                    )
                )
            }
        }
    }

    fun changeStones(toothIds: List<Int>, hasStones: Boolean, pointNum: Int) {
        updateToothParam(toothIds) { tooth, isVestibular ->
            if (isVestibular) {
                tooth.copy(
                    vestibular = tooth.vestibular.copy(
                        stones = updateBoolPoint(tooth.vestibular.stones, pointNum, hasStones)
                    )
                )
            } else {
                tooth.copy(
                    oral = tooth.oral.copy(
                        stones = updateBoolPoint(tooth.oral.stones, pointNum, hasStones)
                    )
                )
            }
        }
    }

    fun changePlaque(toothIds: List<Int>, hasPlaque: Boolean, pointNum: Int) {
        updateToothParam(toothIds) { tooth, isVestibular ->
            if (isVestibular) {
                tooth.copy(
                    vestibular = tooth.vestibular.copy(
                        plaque = updateBoolPoint(tooth.vestibular.plaque, pointNum, hasPlaque)
                    )
                )
            } else {
                tooth.copy(
                    oral = tooth.oral.copy(
                        plaque = updateBoolPoint(tooth.oral.plaque, pointNum, hasPlaque)
                    )
                )
            }
        }
    }

    fun changePus(toothIds: List<Int>, hasPus: Boolean, pointNum: Int) {
        updateToothParam(toothIds) { tooth, isVestibular ->
            if (isVestibular) {
                tooth.copy(
                    vestibular = tooth.vestibular.copy(
                        pus = updateBoolPoint(tooth.vestibular.pus, pointNum, hasPus)
                    )
                )
            } else {
                tooth.copy(
                    oral = tooth.oral.copy(
                        pus = updateBoolPoint(tooth.oral.pus, pointNum, hasPus)
                    )
                )
            }
        }
    }

    fun changeCaries(toothIds: List<Int>, hasCaries: Boolean, cariesType: CariesType) {
        updateToothParam(toothIds) { tooth, isVestibular ->
            if (isVestibular) {
                tooth.copy(
                    vestibular = tooth.vestibular.copy(
                        caries = updateCaries(tooth.vestibular.caries, cariesType, hasCaries)
                    )
                )
            } else {
                tooth.copy(
                    oral = tooth.oral.copy(
                        caries = updateCaries(tooth.oral.caries, cariesType, hasCaries)
                    )
                )
            }
        }
    }

    private inline fun updateToothParam(
        toothIds: List<Int>,
        crossinline updateBlock: (Tooth, Boolean) -> Tooth
    ) {
        val isVestibular = _currentSurfaceType.value == SurfaceType.VESTIBULAR
        val currentTeeth = if (_currentJawType.value == JawType.UPPER) _upTeeth else _lowTeeth

        val updatedTeeth = currentTeeth.value.map { tooth ->
            if (tooth.id in toothIds) {
                updateBlock(tooth, isVestibular)
            } else {
                tooth
            }
        }

        if (_currentJawType.value == JawType.UPPER) {
            _upTeeth.value = updatedTeeth
        } else {
            _lowTeeth.value = updatedTeeth
        }
    }

    private inline fun updateTeeth(
        toothIds: List<Int>,
        crossinline update: (Tooth) -> Tooth
    ) {
        if (_currentJawType.value == JawType.UPPER) {
            _upTeeth.value = _upTeeth.value.map { tooth ->
                if (tooth.id in toothIds) update(tooth) else tooth
            }
        } else {
            _lowTeeth.value = _lowTeeth.value.map { tooth ->
                if (tooth.id in toothIds) update(tooth) else tooth
            }
        }
    }

    private fun updateBoolPoint(points: BoolPoints, pointNum: Int, value: Boolean): BoolPoints {
        return when (pointNum) {
            1 -> points.copy(p1 = value)
            2 -> points.copy(p2 = value)
            3 -> points.copy(p3 = value)
            else -> points
        }
    }

    private fun updateIntPoint(points: IntPoints, pointNum: Int, value: Int): IntPoints {
        return when (pointNum) {
            1 -> points.copy(p1 = value)
            2 -> points.copy(p2 = value)
            3 -> points.copy(p3 = value)
            else -> points
        }
    }

    private fun updateFurcation(furcation: Furcation, value: Int, pointNum: Int): Furcation {
        return when (pointNum) {
            1 -> furcation.copy(root1 = value)
            2 -> furcation.copy(root2 = value)
            else -> furcation
        }
    }

    private fun updateCaries(caries: Caries, cariesType: CariesType, value: Boolean): Caries {
        return when (cariesType) {
            CariesType.C11 -> caries.copy(c11 = value)
            CariesType.C12 -> caries.copy(c12 = value)
            CariesType.C13 -> caries.copy(c13 = value)
            CariesType.C21 -> caries.copy(c21 = value)
            CariesType.C22 -> caries.copy(c22 = value)
            CariesType.C23 -> caries.copy(c23 = value)
        }
    }

    fun finishAppointment(onSuccess: (Appointment) -> Unit) {
        _isLoading.value = true

        val appointment = Appointment(
            complaints = complaints.value,
            comments = comments.value,
            patient = appointment.value?.patient,
            date = appointment.value?.date ?: LocalDateTime.now(),
            diagnosis = diagnosis.value,
            plan = treatmentPlan.value,
            chart = upTeeth.value + lowTeeth.value,
            indexes = Indexes(bop = bop.value, api = api.value, ohis = ohis.value, russel = russel.value, cpitn = cpitn.value, kpu = kpu.value),
            isFirst = appointment.value?.isFirst ?: true
        )

        onSuccess(appointment)

        _isLoading.value = false
    }

}