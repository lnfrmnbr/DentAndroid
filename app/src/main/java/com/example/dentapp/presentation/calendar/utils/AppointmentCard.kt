package com.example.dentapp.presentation.calendar.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.ui.theme.Blue
import com.example.dentapp.ui.theme.LightDarkBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.SeaBlue
import com.example.dentapp.utils.DateUtils

@Composable
fun AppointmentCard(appointment: Appointment) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val date = appointment.date
            Text(
                text = "${DateUtils.toDMMMM(date.toLocalDate())}, ${date.year}",
                color = Blue,
                fontFamily = Montserrat,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )

            Text(
                text = DateUtils.toHHMM(appointment.date),
                color = Blue,
                fontFamily = Montserrat,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Text(
            text = appointment.patientFullName.toString(),
            color = LightDarkBlue,
            fontFamily = Montserrat,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            appointment.tags?.forEach { tag ->
                Text(
                    text = tag,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier
                        .background(SeaBlue, RoundedCornerShape(16.dp))
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                )
            }
        }

        HorizontalLine()
    }
}

@Composable
fun HorizontalLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(SeaBlue)
    )
}