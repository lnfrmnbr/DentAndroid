package com.example.dentapp.presentation.calendar.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dentapp.R
import com.example.dentapp.presentation.calendar.utils.CalendarEvent
import com.example.dentapp.presentation.calendar.utils.CalendarView
import com.example.dentapp.presentation.calendar.viewmodel.CalendarViewModel
import com.example.dentapp.ui.theme.Cygre
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.NewBlue
import com.example.dentapp.utils.StringUtils
import java.time.LocalDate

@Composable
fun CalendarScreen(
    navController: NavHostController,
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val firstName by calendarViewModel.firstName.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = StringUtils.capitalize(stringResource(R.string.calendar)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 24.dp, bottom = 24.dp),
                color = NewBlue,
                fontFamily = Cygre,
                fontSize = 30.sp,
                fontWeight = FontWeight.Normal
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val events = listOf(
                        CalendarEvent(LocalDate.of(2026, 3, 11), "Event"),
                        CalendarEvent(LocalDate.of(2026,3,6),"Event"),
                        CalendarEvent(LocalDate.of(2026,3,12),"Event"),
                        CalendarEvent(LocalDate.of(2026,4,2),"Event"),
                    )

                    CalendarView(
                        events = events,
                        onDayClick = { date ->
                            println("Clicked $date")
                        }
                    )
                }
            }
        }
    }
}