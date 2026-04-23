package com.example.dentapp.presentation.patients.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.dentapp.R
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.presentation.calendar.utils.AppointmentCard
import com.example.dentapp.presentation.navigation.Screen
import com.example.dentapp.ui.theme.DarkBlue
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.LightDarkBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.SkyBlue
import com.example.dentapp.ui.theme.VeryLightBlue
import com.example.dentapp.ui.theme.White
import java.time.LocalDateTime
import java.util.UUID

@Composable
fun PatientsScreen(navController: NavHostController) {}

@Composable
fun PatientScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    )
    {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 38.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.previous_icon),
                            contentDescription = null,
                            tint = LightDarkBlue
                        )
                    }

                    Text(
                        "Иванов Иван Иванович",
                        color = DarkBlue,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_pen),
                            contentDescription = null,
                            tint = LightDarkBlue
                        )
                    }
                }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .border(
                        width = 2.dp,
                        color = SkyBlue,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .background(VeryLightBlue)
                    .padding(12.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.post),
                        contentDescription = null,
                        tint = SkyBlue
                    )

                    Text(
                        "ivanov.ii@yandex.ru",
                        color = DarkBlue,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .border(
                        width = 2.dp,
                        color = SkyBlue,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .background(VeryLightBlue)
                    .padding(12.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.bday),
                        contentDescription = null,
                        tint = SkyBlue,
                        modifier = Modifier.size(28.dp)
                    )

                    Text(
                        "01.01.2000",
                        color = DarkBlue,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                }
            }

            BarChart()
        }

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
                when {
                    else -> {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                "История посещений",
                                color = DarkBlue,
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )

                            Text(
                                "Смотреть в файлах >",
                                color = LightDarkBlue,
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                modifier = Modifier.clickable {
                                    navController.navigate(Screen.Calendar.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                        listOf(
                            Appointment(
                                doctorId = UUID.randomUUID(),
                                patientId = UUID.randomUUID(),
                                patientFullName = "Иванов Иван Иванович",
                                date = LocalDateTime.of(2026, 4, 21, 18, 0),
                                tags = listOf("лечение")
                            ),
                            Appointment(
                                doctorId = UUID.randomUUID(),
                                patientId = UUID.randomUUID(),
                                patientFullName = "Иванов Иван Иванович",
                                date = LocalDateTime.of(2026, 3, 24, 13, 30),
                                tags = listOf("лечение")
                            ),
                            Appointment(
                                doctorId = UUID.randomUUID(),
                                patientId = UUID.randomUUID(),
                                patientFullName = "Иванов Иван Иванович",
                                date = LocalDateTime.of(2026, 3, 12, 12, 30),
                                tags = listOf("чистка")
                            ),
                            Appointment(
                                doctorId = UUID.randomUUID(),
                                patientId = UUID.randomUUID(),
                                patientFullName = "Иванов Иван Иванович",
                                date = LocalDateTime.of(2026, 2, 1, 14, 0),
                                tags = listOf("осмотр")
                            )
                        ).forEach { appointment ->
                            AppointmentCard(appointment)
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                        )
                    }
                }
            }
        }
    }
}
}


@Preview
@Composable
fun BarChart(
    yData: List<Double> = listOf(26.386, 15.839, 9.368, 5.376),
    dates: List<String> = listOf("01.02", "12.03", "24.03", "21.04"),
    modifier: Modifier = Modifier
) {
    val maxValue = ((36.386) ?: 0.0).toFloat()

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .border(
                width = 2.dp,
                color = SkyBlue,
                shape = RoundedCornerShape(24.dp)
            )
            .background(VeryLightBlue)
            .padding(10.dp)
    ) {
        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(150.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                yData.forEachIndexed { index, value ->
                    val heightRatio = (value / maxValue).toFloat()

                    val barColor = when {
                        value < 10 -> Color(0xFF4CAF50) // зелёный
                        value < 25 -> Color(0xFFFFC107) // жёлтый
                        else -> Color(0xFFF44336) // красный
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        // Подпись со значением y над столбцом
                        Text(
                            text = String.format("%.1f", value) + "%",
                            color = DarkBlue,
                            fontFamily = Montserrat,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )

                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .fillMaxHeight(heightRatio)
                                .background(barColor, shape = RoundedCornerShape(4.dp))
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = dates.getOrNull(index) ?: "01.02",
                            color = DarkBlue,
                            fontFamily = Montserrat,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = SkyBlue,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(White)
                        .padding(5.dp)
                ) {
                    Text(
                        text = "КПУ",
                        color = DarkBlue,
                        fontFamily = Montserrat,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(SkyBlue)
                        .padding(5.dp)
                ) {
                    Text(
                        text = "BOP",
                        color = White,
                        fontFamily = Montserrat,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = SkyBlue,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(White)
                        .padding(5.dp)
                ) {
                    Text(
                        text = "API",
                        color = DarkBlue,
                        fontFamily = Montserrat,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = SkyBlue,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(White)
                        .padding(5.dp)
                ) {
                    Text(
                        text = "Russel",
                        color = DarkBlue,
                        fontFamily = Montserrat,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = SkyBlue,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(White)
                        .padding(5.dp)
                ) {
                    Text(
                        text = "OHI-S",
                        color = DarkBlue,
                        fontFamily = Montserrat,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

            }
        }
    }
}