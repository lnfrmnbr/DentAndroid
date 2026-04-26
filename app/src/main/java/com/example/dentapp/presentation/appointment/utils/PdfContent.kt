package com.example.dentapp.presentation.appointment.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dentapp.R
import com.example.dentapp.domain.model.appointment.Appointment
import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.domain.model.clinic.Clinic
import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.presentation.periochart.utils.Tooth
import com.example.dentapp.ui.theme.LightGrey
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.White
import com.example.dentapp.utils.DateUtils
import java.time.LocalDateTime
import java.util.Date

val teethUnit = listOf(
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
    Tooth(28, R.raw.t28),
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

@Composable
fun PdfPage1(
    appointment: Appointment = Appointment(
        patient = Patient(
            fullName = "Иванов Иван Иванович",
            birthDate = Date(2000, 1, 1),
            sex = "M",
            email = "spamovik123@ya.ry",
            phoneNumber = "+79111129019"
        ),
        date = LocalDateTime.now(),
        tags = listOf("осмотр", "брекиты"),
        indexes = Indexes(),
        complaints = "болит всн",
        comments = "комментариииииии",
        plan = "чистка, брекиты, паста когейт",
        isFirst = true,
        diagnosis = listOf("Пульпит", "Кариес"),
        chart = teethUnit
    ),
    clinic: Clinic = Clinic(
        logo = "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",
        orgName = "ООО \"Арт Класс\" СК\"",
        shortName = "Арт Класс",
        contacts = "+7 (812) 492-90-00\n" +
                "+7 (812) 715-82-17\n" +
                "mail@ak-stom.ru",
        address = "Санкт-Петербург, \n" +
                "Ланское шоссе, д. 65",
        email = "spamovik123@ya.ru"
    ),
    doctorFullName: String = "Домбровская Юлия Андреевна"
) {
    Column(
        modifier = Modifier
            .background(White)
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = clinic.orgName,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    Column {
                        Text(
                            text = stringResource(R.string.contacts),
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black
                        )

                        Text(
                            text = clinic.contacts,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }

                    Column {
                        Text(
                            text = stringResource(R.string.address),
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black
                        )

                        Text(
                            text = clinic.address,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
            }
            Image(
                bitmap = base64ToBitmap(clinic.logo).asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Divider(
            color = Color.Black,
            thickness = 1.dp
        )

        Row {
            Column {
                Text(
                    text = stringResource(R.string.appointment_date) + ":",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = stringResource(R.string.patient_full_name) + ":",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = stringResource(R.string.birth_date) + ":",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = stringResource(R.string.doctor) + ":",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = DateUtils.toDDMMYYYY(appointment.date),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = appointment.patient?.fullName ?: "",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Black
                )


                Text(
                    text = appointment.patient?.birthDate?.let {
                        DateUtils.toDDMMYYYY(it)
                    } ?: "",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Black
                )


                Text(
                    text = doctorFullName,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(32.dp))

            Column {
                Text(
                    text = stringResource(R.string.time) + ":",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = stringResource(R.string.gender) + ":",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = stringResource(R.string.age) + ":",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = stringResource(R.string.ids) + ":",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = DateUtils.toHHMM(appointment.date),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = appointment.patient?.sex ?: "",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Black
                )


                Text(
                    text = appointment.patient?.birthDate?.let {
                        DateUtils.countAge(it).toString()
                    } ?: "",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Black
                )


                Text(
                    text = stringResource(R.string.got),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(LightGrey)
                .padding(5.dp)
        ) {
            Text(
                text = stringResource(R.string.diagnose),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
        }

        Row {
            Text(
                text = stringResource(R.string.patients_complains) + ":",
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = appointment.complaints ?: "",
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Row {
            Text(
                text = stringResource(R.string.diagnose) + ":",
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = appointment.diagnosis?.joinToString(", ") ?: "",
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        PerioChartField(chart = appointment.chart!!)
    }
}

private fun base64ToBitmap(base64: String): Bitmap {
    val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}