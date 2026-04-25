package com.example.dentapp.presentation.appointment.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dentapp.presentation.periochart.utils.TeethRow
import com.example.dentapp.presentation.periochart.utils.Tooth
import com.example.dentapp.ui.theme.Grey
import com.example.dentapp.ui.theme.White

@Composable
fun PerioChartField(chart: List<Tooth>) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(
                width = 1.dp,
                color = Grey,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            TeethRow(chart.filter { it.id < 30 }, false, 150.dp) {}
            TeethRow(chart.filter { it.id > 30 }, false, 150.dp) {}
        }
    }
}