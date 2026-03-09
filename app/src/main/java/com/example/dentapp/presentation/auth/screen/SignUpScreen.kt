package com.example.dentapp.presentation.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dentapp.R
import com.example.dentapp.presentation.auth.viewmodel.SignUpViewModel
import com.example.dentapp.ui.theme.Blue
import com.example.dentapp.ui.theme.Cygre
import com.example.dentapp.ui.theme.DarkBlue
import com.example.dentapp.ui.theme.DarkCyan
import com.example.dentapp.ui.theme.DarkGrey
import com.example.dentapp.ui.theme.GreyBlue
import com.example.dentapp.ui.theme.LightDarkBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.NewBlue
import com.example.dentapp.ui.theme.Pink
import com.example.dentapp.ui.theme.VeryDarkBlue
import com.example.dentapp.ui.theme.White
import kotlin.collections.listOf

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onSignUpSuccess: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    val email by viewModel.email.collectAsState()
    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val patronymic by viewModel.patronymic.collectAsState()
    val sex by viewModel.sex.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val birthDate by viewModel.birthDate.collectAsState()
    val position by viewModel.position.collectAsState()

    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val confirmPasswordVisible = rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to NewBlue,
                        0.7f to Color.White
                    )
                )
            )
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(48.dp))

                Image(
                    painter = painterResource(id = R.drawable.auth_logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.7f)
                        .drawWithContent { drawContent() }
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = stringResource(R.string.sign_up),
                    fontFamily = Cygre,
                    fontWeight = FontWeight.Normal,
                    fontSize = 40.sp,
                    color = LightDarkBlue
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.full_name),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = LightDarkBlue,
                    modifier = Modifier.align(Alignment.Start)
                )

                InputTextField(lastName, R.string.lastName) { viewModel.updateLastName(it) }

                InputTextField(firstName, R.string.name) { viewModel.updateFirstName(it) }

                InputTextField(patronymic, R.string.patronymic) { viewModel.updatePatronymic(it) }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = stringResource(R.string.about_you),
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
                        selected = sex,
                        values = listOf(
                            stringResource(R.string.gender_male),
                            stringResource(R.string.gender_female)
                        ),
                        onSelect = { viewModel.updateSex(it) },
                        modifier = Modifier.weight(0.35f)
                    )

                    OutlinedTextField(
                        value = birthDate,
                        onValueChange = { input: String ->
                            val digits = input.filter { it.isDigit() }.take(8)
                            viewModel.updateBirthDate(digits)
                        },
                        label = {
                            Text(
                                stringResource(R.string.birth_date),
                                color = DarkGrey,
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        },
                        visualTransformation = DateVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedLabelColor = DarkGrey,
                            unfocusedLabelColor = DarkGrey,
                            focusedIndicatorColor = DarkGrey,
                            unfocusedIndicatorColor = DarkGrey,
                            focusedTextColor = DarkGrey,
                            unfocusedTextColor = DarkGrey
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(0.7f)
                    )
                }

                DropdownField(
                    title = stringResource(R.string.position),
                    selected = position,
                    values = listOf(
                        "Администратор",
                        "Стоматолог"
                    ),
                    onSelect = { viewModel.updatePosition(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = stringResource(R.string.email),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = LightDarkBlue,
                    modifier = Modifier.align(Alignment.Start)
                )

                InputTextField(email, R.string.email) { viewModel.updateEmail(it) }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = stringResource(R.string.password),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = LightDarkBlue,
                    modifier = Modifier.align(Alignment.Start)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.updatePassword(it) },
                    label = {
                        Text(
                            stringResource(R.string.password),
                            color = DarkGrey,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisible.value = !passwordVisible.value
                        }) {
                            Icon(
                                imageVector = if (passwordVisible.value) ImageVector.vectorResource(
                                    id = R.drawable.visibility_on
                                ) else ImageVector.vectorResource(id = R.drawable.visibility_off),
                                contentDescription = if (passwordVisible.value) stringResource(R.string.hide_password) else stringResource(
                                    R.string.show_password
                                ),
                                tint = DarkGrey
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedLabelColor = DarkGrey,
                        unfocusedLabelColor = DarkGrey,
                        focusedIndicatorColor = DarkGrey,
                        unfocusedIndicatorColor = DarkGrey,
                        focusedTextColor = DarkGrey,
                        unfocusedTextColor = DarkGrey
                    ),
                    shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { viewModel.updateConfirmPassword(it) },
                    label = {
                        Text(
                            stringResource(R.string.submit_password),
                            color = DarkGrey,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            confirmPasswordVisible.value = !confirmPasswordVisible.value
                        }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible.value) ImageVector.vectorResource(
                                    id = R.drawable.visibility_on
                                ) else ImageVector.vectorResource(id = R.drawable.visibility_off),
                                contentDescription = if (confirmPasswordVisible.value) stringResource(
                                    R.string.hide_password
                                ) else stringResource(R.string.show_password),
                                tint = DarkGrey
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedLabelColor = DarkGrey,
                        unfocusedLabelColor = DarkGrey,
                        focusedIndicatorColor = DarkGrey,
                        unfocusedIndicatorColor = DarkGrey,
                        focusedTextColor = DarkGrey,
                        unfocusedTextColor = DarkGrey
                    ),
                    shape = RoundedCornerShape(16.dp)
                )

                errorMessage?.let { message ->
                    Text(
                        text = message,
                        color = Pink,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.signUp(onSignUpSuccess) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = !isLoading && email.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue,
                        contentColor = Color.White,
                        disabledContainerColor = Blue,
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
                            text = stringResource(R.string.do_sign_up),
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.have_account),
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        color = VeryDarkBlue
                    )
                    Text(
                        text = stringResource(R.string.do_sign_in),
                        fontFamily = Montserrat,
                        color = VeryDarkBlue,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onNavigateToSignIn() }
                    )
                }
            }
        }
    }
}

@Composable
fun InputTextField(
    value: String,
    stringResource: Int,
    icon: Int? = null,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(stringResource(stringResource), color = DarkGrey) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            errorContainerColor = GreyBlue,
            focusedLabelColor = DarkGrey,
            unfocusedLabelColor = DarkGrey,
            focusedIndicatorColor = DarkGrey,
            unfocusedIndicatorColor = DarkGrey,
            focusedTextColor = DarkGrey,
            unfocusedTextColor = DarkGrey
        ),
        shape = RoundedCornerShape(16.dp),
        leadingIcon = if (icon != null) {
            {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = DarkGrey
                )
            }
        } else null
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    title: String,
    selected: String,
    onSelect: (String) -> Unit,
    values: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            label = {
                Text(
                    text = title,
                    color = DarkGrey,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLabelColor = DarkGrey,
                unfocusedLabelColor = DarkGrey,
                focusedIndicatorColor = DarkGrey,
                unfocusedIndicatorColor = DarkGrey,
                focusedTextColor = DarkGrey,
                unfocusedTextColor = DarkGrey,
                disabledTextColor = DarkGrey,
                disabledContainerColor = Color.White,
                disabledIndicatorColor = DarkGrey,
                disabledLabelColor = DarkGrey
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(DarkGrey)
        ) {
            values.forEach { gender ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = gender,
                            color = Color.White,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        onSelect(gender)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}