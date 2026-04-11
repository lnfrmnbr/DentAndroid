package com.example.dentapp.presentation.periochart.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dentapp.R
import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.presentation.appointment.screen.PatientSearchDropdown
import com.example.dentapp.presentation.auth.screen.InputTextField
import com.example.dentapp.presentation.navigation.Screen
import com.example.dentapp.presentation.periochart.utils.ActionBar
import com.example.dentapp.presentation.periochart.utils.DiagnosisField
import com.example.dentapp.presentation.periochart.utils.IndexesTable
import com.example.dentapp.presentation.periochart.utils.SlidingTeethBox
import com.example.dentapp.presentation.periochart.utils.ToothUiModel
import com.example.dentapp.ui.theme.Blue
import com.example.dentapp.ui.theme.DarkBlue
import com.example.dentapp.ui.theme.Grey
import com.example.dentapp.ui.theme.GreyBlue
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.Pink
import com.example.dentapp.ui.theme.White

val upperJaw = listOf(
    ToothUiModel(18, R.raw.t18),
    ToothUiModel(17, R.raw.t17),
    ToothUiModel(16, R.raw.t16),
    ToothUiModel(15, R.raw.t15),
    ToothUiModel(14, R.raw.t14),
    ToothUiModel(13, R.raw.t13),
    ToothUiModel(12, R.raw.t12),
    ToothUiModel(11, R.raw.t11),
    ToothUiModel(21, R.raw.t21),
    ToothUiModel(22, R.raw.t22),
    ToothUiModel(23, R.raw.t23),
    ToothUiModel(24, R.raw.t24),
    ToothUiModel(25, R.raw.t25),
    ToothUiModel(26, R.raw.t26),
    ToothUiModel(27, R.raw.t27),
    ToothUiModel(28, R.raw.t28)
)

@Composable
fun PerioChartScreen(navController: NavHostController) {

    val patients = listOf<Patient>()//by viewModel.patients.collectAsState()

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
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ActionBar()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                SlidingTeethBox(upperJaw, upperJaw) { navController.navigate(Screen.DetailToothScreen.route) }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    PatientSearchDropdown(
                        value = "fio",
                        onValueChanged = { newValue ->
                            //viewModel.updateFio(newValue)
                            //viewModel.clearSelectedPatient()
                        },
                        onPatientSelected = { patient ->
                            //viewModel.selectPatient(patient)
                        },
                        patients = patients,
                        fieldsColors = fieldsColors,
                        strRes = R.string.fio
                    )

                    DiagnosisField(
                        "Диагноз", listOf("болезнь1", "болезнь2"),
                        onDeleteDiagnose = {}, {}
                    )

                    IndexesTable()

                    InputTextField(
                        "complaints",
                        R.string.complaints,
                        fieldsColors,
                        deleteSpaces = false
                    ) {
                        // viewModel.updateComplaints(it)
                    }

                    InputTextField(
                        "plan",
                        R.string.treatment_plan,
                        fieldsColors,
                        deleteSpaces = false
                    ) {
                        //viewModel.updateComments(it)
                    }

                    InputTextField(
                        "comments",
                        R.string.comments,
                        fieldsColors,
                        deleteSpaces = false
                    ) {
                        //viewModel.updateComments(it)
                    }

                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        /*enabled = !isLoading && fio.isNotEmpty() && sex.isNotEmpty()
                                && birthDate.text.isNotEmpty() && (email.isNotEmpty() || phoneNumber.text.isNotEmpty()),*/
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue,
                            contentColor = Color.White,
                            disabledContainerColor = Blue.copy(alpha = 0.5f),
                            disabledContentColor = Color.White.copy(alpha = 0.7f)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        if (false) {//isLoading
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
                                    painter = painterResource(R.drawable.check_mark_icon),
                                    contentDescription = null,
                                    tint = White
                                )

                                Text(
                                    text = stringResource(R.string.complete_examination),
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
    }
}