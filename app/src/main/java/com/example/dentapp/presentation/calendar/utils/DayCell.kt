package com.example.dentapp.presentation.calendar.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dentapp.R
import com.example.dentapp.ui.theme.DarkBlue
import com.example.dentapp.ui.theme.Grey
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.Pink
import com.example.dentapp.ui.theme.White
import java.time.DayOfWeek
import java.time.LocalDate


@Composable
fun DayCell(
    day: CalendarDay,
    onClick: (LocalDate) -> Unit
) {

    val isWeekend =
        day.date.dayOfWeek == DayOfWeek.SATURDAY ||
                day.date.dayOfWeek == DayOfWeek.SUNDAY

    val background =
        when {
            day.isSelected -> DarkBlue
            else -> Color.Transparent
        }

    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))

            .drawBehind {
                if (day.isToday) {

                    val stroke = Stroke(
                        width = 3.5.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(20f,12f), 0f
                        )
                    )

                    drawRoundRect(
                        color = DarkBlue,
                        style = stroke,
                        cornerRadius = CornerRadius(8.dp.toPx())
                    )
                }
            }

            .border(
                width = if (day.events.isNotEmpty() && !day.isToday) 1.5.dp else -1.dp,
                color = when {
                    !day.isCurrentMonth -> LightGray
                    else -> DarkBlue
                },
                shape = RoundedCornerShape(8.dp)
            )

            .background(background)
            .clickable { onClick(day.date) },
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = day.date.dayOfMonth.toString(),
                color =
                    when {
                        day.isSelected -> Color.White
                        !day.isCurrentMonth -> LightGray
                        isWeekend -> Pink
                        else -> DarkBlue
                    },
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun DayOfWeekCell(dayOfWeek: String) {
    val isWeekend =
        dayOfWeek == stringResource(R.string.sat) ||
                dayOfWeek == stringResource(R.string.sun)

    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(White),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = dayOfWeek,
                color =
                    when {
                        isWeekend -> Pink
                        else -> Grey
                    },
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}