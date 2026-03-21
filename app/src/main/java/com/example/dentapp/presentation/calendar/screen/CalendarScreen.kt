package com.example.dentapp.presentation.calendar.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dentapp.R
import com.example.dentapp.presentation.calendar.utils.AppointmentCard
import com.example.dentapp.presentation.calendar.utils.CalendarView
import com.example.dentapp.presentation.calendar.viewmodel.CalendarViewModel
import com.example.dentapp.presentation.common.ErrorBlock
import com.example.dentapp.presentation.common.LoadingBlock
import com.example.dentapp.ui.theme.Cygre
import com.example.dentapp.ui.theme.DarkBlue
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.NewBlue
import com.example.dentapp.utils.DateUtils
import com.example.dentapp.utils.StringUtils

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalendarScreen(
    navController: NavHostController,
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val appointments by calendarViewModel.appointments.collectAsState()
    val daySelected by calendarViewModel.daySelected.collectAsState()
    val appointmentsForDay by calendarViewModel.appointmentsForDay.collectAsState()
    val isLoading by calendarViewModel.isLoading.collectAsState()
    val error by calendarViewModel.error.collectAsState()

    val refreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { calendarViewModel.loadAppointments() }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
            .pullRefresh(refreshState)
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

                    CalendarView(
                        events = appointments,
                        onDayClick = { date ->
                            calendarViewModel.selectDay(date)
                        },
                        selectedDay = daySelected
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Записи на ${DateUtils.toDMMMM(daySelected)}",
                            color = DarkBlue,
                            fontFamily = Montserrat,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(id = R.drawable.plus_round),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }
                    }

                    when {
                        isLoading -> LoadingBlock()
                        error != null -> ErrorBlock(error ?: "Неизвестная ошибка")
                        else -> {

                            appointmentsForDay.forEach { appointment ->
                                AppointmentCard(appointment)
                            }

                            Box(modifier = Modifier.fillMaxWidth().height(30.dp))
                        }
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isLoading,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = Color.White,
            contentColor = DarkBlue,
            scale = true
        )
    }
}