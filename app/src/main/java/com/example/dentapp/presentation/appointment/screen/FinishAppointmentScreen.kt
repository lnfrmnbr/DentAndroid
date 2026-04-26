package com.example.dentapp.presentation.appointment.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dentapp.R
import com.example.dentapp.presentation.appointment.utils.PerioChartField
import com.example.dentapp.presentation.appointment.viewmodel.AppointmentViewModel
import com.example.dentapp.presentation.auth.screen.DropdownField
import com.example.dentapp.presentation.auth.screen.InputTextField
import com.example.dentapp.presentation.navigation.Screen
import com.example.dentapp.presentation.periochart.utils.DiagnosisField
import com.example.dentapp.presentation.periochart.utils.IndexesTable
import com.example.dentapp.ui.theme.Blue
import com.example.dentapp.ui.theme.Cygre
import com.example.dentapp.ui.theme.DarkBlue
import com.example.dentapp.ui.theme.Grey
import com.example.dentapp.ui.theme.GreyBlue
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.LightDarkBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.Pink
import com.example.dentapp.ui.theme.White

@Composable
fun FinishAppointmentScreen(
    navController: NavHostController,
    viewModel: AppointmentViewModel = hiltViewModel()
) {
    val isFirstAppointment by viewModel.isFirstAppointment.collectAsState()
    val email by viewModel.email.collectAsState()
    val sex by viewModel.sex.collectAsState()
    val comments by viewModel.comments.collectAsState()
    val complaints by viewModel.complaints.collectAsState()
    val fio by viewModel.fio.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val patients by viewModel.patients.collectAsState()
    val patientId by viewModel.patientId.collectAsState()
    val birthDateFromVm by viewModel.birthDate.collectAsState()
    val phoneNumberFromVm by viewModel.phoneNumber.collectAsState()
    val diagnosis by viewModel.diagnosis.collectAsState()
    val indexes by viewModel.indexes.collectAsState()
    val plan by viewModel.treatmentPlan.collectAsState()
    val chart by viewModel.chart.collectAsState()

    var birthDate by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var phoneNumber by remember {
        mutableStateOf(TextFieldValue(""))
    }

    LaunchedEffect(birthDateFromVm) {
        if (birthDate.text != birthDateFromVm) {
            birthDate = TextFieldValue(
                text = birthDateFromVm,
                selection = TextRange(birthDateFromVm.length)
            )
        }
    }

    LaunchedEffect(phoneNumberFromVm) {
        val formattedPhone = formatPhoneNumber(phoneNumberFromVm)

        if (phoneNumber.text != formattedPhone) {
            phoneNumber = TextFieldValue(
                text = formattedPhone,
                selection = TextRange(formattedPhone.length)
            )
        }
    }

    val fieldsColors: TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedLabelColor = Grey,
        errorContainerColor = GreyBlue,
        unfocusedLabelColor = Grey,
        focusedIndicatorColor = Grey,
        unfocusedIndicatorColor = Grey,
        errorIndicatorColor = Pink,
        errorLabelColor = Pink,
        focusedTextColor = DarkBlue,
        unfocusedTextColor = DarkBlue,
        disabledTextColor = White,
        disabledContainerColor = DarkBlue
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 56.dp),
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


                Row {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.attach_file_icon),
                            contentDescription = null,
                            tint = LightDarkBlue
                        )
                    }

                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.more_icon),
                            contentDescription = null,
                            tint = LightDarkBlue
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.finish_appointment),
                fontFamily = Cygre,
                fontWeight = FontWeight.Normal,
                fontSize = 40.sp,
                color = LightDarkBlue
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.patient_full_name),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = LightDarkBlue,
                modifier = Modifier.align(Alignment.Start)
            )

            PatientSearchDropdown(
                value = fio,
                onValueChanged = { newValue ->
                    viewModel.updateFio(newValue)
                    viewModel.clearSelectedPatient()
                },
                onPatientSelected = { patient ->
                    viewModel.selectPatient(patient)
                },
                patients = patients,
                fieldsColors = fieldsColors,
                strRes = R.string.patient_full_name
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.patient_info),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = LightDarkBlue,
                modifier = Modifier.align(Alignment.Start)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                DropdownField(
                    title = stringResource(R.string.gender),
                    selected = when (sex) {
                        "" -> ""
                        "M", "М" -> stringResource(R.string.gender_male)
                        else -> stringResource(R.string.gender_female)
                    },
                    values = listOf(
                        stringResource(R.string.gender_male),
                        stringResource(R.string.gender_female)
                    ),
                    onSelect = { viewModel.updateSex(it) },
                    modifier = Modifier.weight(0.4f),
                    fieldsColors = fieldsColors,
                    enabled = patientId == null
                )

                OutlinedTextField(
                    value = birthDate,
                    onValueChange = { input ->

                        val digits = input.text.filter { it.isDigit() }.take(8)

                        val formatted = buildString {
                            for (i in digits.indices) {
                                append(digits[i])
                                if (i == 1 && digits.length > 2) append(".")
                                if (i == 3 && digits.length > 4) append(".")
                            }
                        }

                        birthDate = TextFieldValue(
                            text = formatted,
                            selection = TextRange(formatted.length)
                        )

                        viewModel.updateBirthDate(formatted)
                    },
                    label = {
                        Text(
                            stringResource(R.string.birth_date),
                            color = DarkBlue,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = fieldsColors,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.weight(0.6f),
                    enabled = patientId == null
                )
            }

            InputTextField(
                email,
                R.string.email,
                fieldsColors,
                enabled = patientId == null
            ) { viewModel.updateEmail(it) }

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { input ->
                    val digits = input.text.filter { it.isDigit() }

                    val limitedDigits = digits.take(11)

                    val formatted = buildString {
                        for (i in limitedDigits.indices) {
                            when (i) {
                                0 -> append("+${limitedDigits[i]}")
                                1 -> append(" (${limitedDigits[i]}")
                                4 -> append(") ${limitedDigits[i]}")
                                else -> {
                                    when (i) {
                                        7, 9 -> append("-${limitedDigits[i]}")
                                        else -> append(limitedDigits[i])
                                    }
                                }
                            }
                        }
                    }

                    phoneNumber = TextFieldValue(
                        text = formatted,
                        selection = TextRange(formatted.length)
                    )

                    viewModel.updatePhoneNumber(limitedDigits)
                },
                label = {
                    Text(
                        stringResource(R.string.phone_number),
                        color = DarkBlue,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                },
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                colors = fieldsColors,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                enabled = patientId == null
            )

            Spacer(modifier = Modifier.height(4.dp))

            CheckOption(
                text = "Первичное обследование",
                checked = isFirstAppointment,
                onCheckedChange = { viewModel.updateIsFirstAppointment(it) }
            )

            CheckOption(
                text = "Переоценка",
                checked = !isFirstAppointment,
                onCheckedChange = { viewModel.updateIsFirstAppointment(!it) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.anamnesis),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = LightDarkBlue,
                modifier = Modifier.align(Alignment.Start)
            )

            InputTextField(
                complaints,
                R.string.complaints,
                fieldsColors,
                deleteSpaces = false
            ) { viewModel.updateComplaints(it) }

            Spacer(modifier = Modifier.height(4.dp))

            DiagnosisField(
                stringResource(R.string.diagnose),
                defaultDiagnosis = viewModel.getAllDiseases(),
                diagnosis = diagnosis,
                onDeleteDiagnose = { diagnose -> viewModel.deleteDiagnose(diagnose) },
                onAddDiagnosis = { diagnose -> viewModel.addDiagnose(diagnose) }
            )

            IndexesTable(indexes)

            InputTextField(
                plan,
                R.string.treatment_plan,
                fieldsColors,
                deleteSpaces = false
            ) {
                viewModel.updateTreatmentPlan(it)
            }

            Spacer(modifier = Modifier.height(4.dp))

            PerioChartField(chart)

            InputTextField(
                comments,
                R.string.comments,
                fieldsColors,
                deleteSpaces = false
            ) { viewModel.updateComments(it) }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.saveAppointment {
                        navController.navigate(Screen.Home.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = !isLoading && fio.isNotEmpty() && sex.isNotEmpty()
                        && birthDate.text.isNotEmpty() && (email.isNotEmpty() || phoneNumber.text.isNotEmpty()),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = Color.Blue,
                    disabledContainerColor = White.copy(alpha = 0.5f),
                    disabledContentColor = Color.Blue.copy(alpha = 0.7f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.Blue,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = stringResource(R.string.save_draft),
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Blue
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    viewModel.saveAppointment {
                        navController.navigate(Screen.Report.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = !isLoading && fio.isNotEmpty() && sex.isNotEmpty()
                        && birthDate.text.isNotEmpty() && (email.isNotEmpty() || phoneNumber.text.isNotEmpty()),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue,
                    contentColor = Color.White,
                    disabledContainerColor = Blue.copy(alpha = 0.5f),
                    disabledContentColor = Color.White.copy(alpha = 0.7f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = stringResource(R.string.finish_and_create_report),
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = White
                    )
                }
            }
        }
    }
}