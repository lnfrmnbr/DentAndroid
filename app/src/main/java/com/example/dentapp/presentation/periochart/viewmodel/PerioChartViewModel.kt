package com.example.dentapp.presentation.periochart.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dentapp.R
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

    fun changeRemoved(toothIds: List<Int>, isRemoved: Boolean) {
        if (_currentJawType.value == JawType.UPPER) {
            _upTeeth.value = _upTeeth.value.map {
                if (it.id in toothIds) it.copy(removed = isRemoved) else it
            }
        } else {
            _lowTeeth.value = _lowTeeth.value.map {
                if (it.id in toothIds) it.copy(removed = isRemoved) else it
            }
        }
    }

    fun changeIsImplant(toothIds: List<Int>, isImplant: Boolean) {
        if (_currentJawType.value == JawType.UPPER) {
            _upTeeth.value = _upTeeth.value.map {
                if (it.id in toothIds) it.copy(isImplant = isImplant) else it
            }
        } else {
            _lowTeeth.value = _lowTeeth.value.map {
                if (it.id in toothIds) it.copy(isImplant = isImplant) else it
            }
        }
    }

}