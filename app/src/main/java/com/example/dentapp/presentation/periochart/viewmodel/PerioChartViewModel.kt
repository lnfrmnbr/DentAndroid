package com.example.dentapp.presentation.periochart.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dentapp.R
import com.example.dentapp.presentation.periochart.utils.BoolPoints
import com.example.dentapp.presentation.periochart.utils.ToothUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class PerioChartViewModel @Inject constructor() : ViewModel() {
    val upTeethUnit = listOf(
        ToothUiModel(18, R.raw.t18),
        ToothUiModel(17, R.raw.t17),
        ToothUiModel(16, R.raw.t16),
        ToothUiModel(15, R.raw.t15),
        ToothUiModel(14, R.raw.t14),
        ToothUiModel(13, R.raw.t13),
        ToothUiModel(12, R.raw.t12),
        ToothUiModel(11, R.raw.t11),
        ToothUiModel(21, R.raw.t21),
        ToothUiModel(22, R.raw.t22),
        ToothUiModel(23, R.raw.t23),
        ToothUiModel(24, R.raw.t24),
        ToothUiModel(25, R.raw.t25),
        ToothUiModel(26, R.raw.t26),
        ToothUiModel(27, R.raw.t27),
        ToothUiModel(28, R.raw.t28)
    )

    private val _upTeeth = MutableStateFlow(upTeethUnit.map { it.copy() })
    val upTeeth: StateFlow<List<ToothUiModel>> = _upTeeth.asStateFlow()

    private val _lowTeeth = MutableStateFlow(upTeethUnit.map { it.copy() })
    val lowTeeth: StateFlow<List<ToothUiModel>> = _lowTeeth.asStateFlow()

    private val _selectedTeeth = MutableStateFlow<List<Int>>(emptyList())
    val selectedTeeth: StateFlow<List<Int>> = _selectedTeeth.asStateFlow()

    private val _currentJawType = MutableStateFlow<JawType>(JawType.UPPER)
    val currentJawType: StateFlow<JawType> = _currentJawType.asStateFlow()

    private val _currentSurfaceType = MutableStateFlow<SurfaceType>(SurfaceType.VESTIBULAR)
    val currentSurfaceType: StateFlow<SurfaceType> = _currentSurfaceType.asStateFlow()

    enum class JawType { UPPER, LOWER }
    enum class SurfaceType { VESTIBULAR, ORAL }

    fun selectTooth(toothId: Int) {
        _selectedTeeth.value = _selectedTeeth.value + toothId
    }

    fun clearSelection() {
        _selectedTeeth.value = emptyList()
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

    private inline fun updateToothParam(
        toothIds: List<Int>,
        crossinline updateBlock: (ToothUiModel, Boolean) -> ToothUiModel
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
        crossinline update: (ToothUiModel) -> ToothUiModel
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

}