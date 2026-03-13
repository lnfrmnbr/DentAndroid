package com.example.dentapp.presentation.calendar.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dentapp.R
import com.example.dentapp.presentation.calendar.utils.CalendarUtil.generateMonthDays
import com.example.dentapp.presentation.calendar.utils.CalendarUtil.weekNumber
import com.example.dentapp.ui.theme.Grey
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.SeaBlue
import com.example.dentapp.ui.theme.White
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun CalendarView(
    events: List<CalendarEvent>,
    onDayClick: (LocalDate) -> Unit
) {

    var month by remember { mutableStateOf(YearMonth.now()) }

    val weeks = remember(month, events) {
        generateMonthDays(month, events)
    }

    val daysOfWeek = listOf(
        stringResource(R.string.mon),
        stringResource(R.string.tue),
        stringResource(R.string.wed),
        stringResource(R.string.thu),
        stringResource(R.string.fri),
        stringResource(R.string.sat),
        stringResource(R.string.sun)
    )

    val formatter = DateTimeFormatter.ofPattern("MM")

    Column {

        CalendarHeader(
            yearMonth = month,
            onPrev = { month = month.minusMonths(1) },
            onNext = { month = month.plusMonths(1) }
        )

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(White),
                contentAlignment = Alignment.Center
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = month.format(formatter).toString(),
                        color = Grey,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp
                    )
                }
            }

            daysOfWeek.forEach { dayOfWeek -> DayOfWeekCell(dayOfWeek) }
        }

        Spacer(Modifier.height(6.dp))

        weeks.forEach { week ->

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                val isCurrentWeek = week.any { it.isToday }

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isCurrentWeek) SeaBlue else LightBlue),
                    contentAlignment = Alignment.Center
                ) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Text(
                            text = weekNumber(week.first().date).toString(),
                            color = White,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        )
                    }
                }

                week.forEach { day ->

                    DayCell(
                        day = day,
                        onClick = onDayClick
                    )
                }
            }

            Spacer(Modifier.height(6.dp))
        }
    }
}