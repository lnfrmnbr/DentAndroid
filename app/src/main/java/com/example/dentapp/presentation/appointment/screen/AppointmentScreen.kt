package com.example.dentapp.presentation.appointment.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
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
import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.presentation.appointment.viewmodel.AppointmentViewModel
import com.example.dentapp.presentation.auth.screen.DropdownField
import com.example.dentapp.presentation.auth.screen.InputTextField
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
import com.example.dentapp.utils.DateUtils
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppointmentScreen(
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
    val birthDateFromVm by viewModel.birthDate.collectAsState()
    val phoneNumberFromVm by viewModel.phoneNumber.collectAsState()
    val patientId by viewModel.patientId.collectAsState()

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

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setNavigationBarColor(
            color = LightBlue,
            darkIcons = true
        )
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
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.start_appointment),
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
                fieldsColors = fieldsColors
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

            InputTextField(email, R.string.email, fieldsColors, enabled = patientId == null) { viewModel.updateEmail(it) }

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

            InputTextField(
                comments,
                R.string.comments,
                fieldsColors,
                deleteSpaces = false
            ) { viewModel.updateComments(it) }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.startAppointment {} },
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
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.start_icon),
                            contentDescription = null,
                            tint = White
                        )

                        Text(
                            text = stringResource(R.string.start_appointment),
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
}

@Composable
fun CheckOption(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = DarkBlue,
                    uncheckedColor = Gray,
                    checkmarkColor = White
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text,
                color = DarkBlue,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun PatientSearchDropdown(
    value: String,
    onValueChanged: (String) -> Unit,
    onPatientSelected: (Patient) -> Unit,
    patients: List<Patient>,
    fieldsColors: TextFieldColors
) {
    var searchText by remember { mutableStateOf(value) }
    var isExpanded by remember { mutableStateOf(false) }

    val filteredPatients = remember(searchText, patients) {
        patients.filter {
            it.fullName.contains(searchText, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {

        OutlinedTextField(
            value = searchText,
            onValueChange = { text ->
                searchText = text
                onValueChanged(text)

                isExpanded = text.isNotEmpty()
            },
            label = {
                Text(
                    stringResource(R.string.patient_full_name),
                    color = DarkBlue,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            },
            textStyle = LocalTextStyle.current.copy(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = DarkBlue
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = fieldsColors,
            shape = RoundedCornerShape(16.dp)
        )

        if (isExpanded && searchText.isNotEmpty()) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp),
                shape = RoundedCornerShape(12.dp),
                color = White,
                tonalElevation = 4.dp
            ) {
                LazyColumn {

                    if (filteredPatients.isEmpty()) {
                        item {
                            Text(
                                text = "Это новый пациент",
                                color = DarkBlue,
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    } else {
                        items(filteredPatients.take(10)) { patient ->

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        searchText = patient.fullName
                                        onValueChanged(patient.fullName)
                                        onPatientSelected(patient)

                                        isExpanded = false
                                    }
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = patient.fullName,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkBlue,
                                    fontFamily = Montserrat,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = DateUtils.toDDMMYYYY(patient.birthDate),
                                    color = DarkBlue,
                                    fontFamily = Montserrat,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatPhoneNumber(phoneDigits: String): String {
    if (phoneDigits.isEmpty()) return ""

    val formatted = buildString {
        for (i in phoneDigits.indices) {
            when (i) {
                0 -> append("+${phoneDigits[i]}")
                1 -> append(" (${phoneDigits[i]}")
                4 -> append(") ${phoneDigits[i]}")
                else -> {
                    when (i) {
                        7, 9 -> append("-${phoneDigits[i]}")
                        else -> append(phoneDigits[i])
                    }
                }
            }
        }
    }
    return formatted
}