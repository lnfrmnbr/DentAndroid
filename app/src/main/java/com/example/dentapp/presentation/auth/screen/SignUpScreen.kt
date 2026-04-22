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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dentapp.R
import com.example.dentapp.presentation.auth.viewmodel.SignUpViewModel
import com.example.dentapp.ui.theme.Blue
import com.example.dentapp.ui.theme.Cygre
import com.example.dentapp.ui.theme.DarkBlue
import com.example.dentapp.ui.theme.Grey
import com.example.dentapp.ui.theme.GreyBlue
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.LightDarkBlue
import com.example.dentapp.ui.theme.LightGreyBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.Pink
import com.example.dentapp.ui.theme.VeryDarkBlue
import com.example.dentapp.ui.theme.White

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
    val position by viewModel.position.collectAsState()
    var birthDate by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val confirmPasswordVisible = rememberSaveable { mutableStateOf(false) }

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
        unfocusedTextColor = DarkBlue
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
            .verticalScroll(rememberScrollState())
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

            Spacer(modifier = Modifier.height(16.dp))

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

            InputTextField(lastName, R.string.lastName, fieldsColors) { viewModel.updateLastName(it) }

            InputTextField(firstName, R.string.name, fieldsColors) { viewModel.updateFirstName(it) }

            InputTextField(patronymic, R.string.patronymic, fieldsColors) { viewModel.updatePatronymic(it) }

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
                    modifier = Modifier.weight(0.4f),
                    fieldsColors = fieldsColors
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
                        fontSize = 16.sp,
                        color = DarkBlue
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = fieldsColors,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.weight(0.6f)
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
                modifier = Modifier.fillMaxWidth(),
                fieldsColors = fieldsColors
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

            InputTextField(email, R.string.email, fieldsColors) { viewModel.updateEmail(it) }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.password),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = LightDarkBlue,
                modifier = Modifier.align(Alignment.Start)
            )

            PasswordField(
                password = password,
                label = stringResource(R.string.password),
                passwordError = null,
                passwordVisible = passwordVisible,
                fieldsColors = fieldsColors
            ) { viewModel.updatePassword(it) }

            Text(
                text = stringResource(R.string.password_req),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                color = LightGreyBlue,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            PasswordField(
                password = confirmPassword,
                label = stringResource(R.string.submit_password),
                passwordError = null,
                passwordVisible = confirmPasswordVisible,
                fieldsColors = fieldsColors
            ) { viewModel.updateConfirmPassword(it) }

            errorMessage?.let { message ->
                Text(
                    text = message,
                    color = Pink,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.Start)
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
                    color = VeryDarkBlue,
                    fontSize = 14.sp
                )
                Text(
                    text = stringResource(R.string.do_sign_in),
                    fontFamily = Montserrat,
                    color = VeryDarkBlue,
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onNavigateToSignIn() }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun InputTextField(
    value: String,
    stringResource: Int,
    fieldsColors: TextFieldColors,
    icon: Int? = null,
    deleteSpaces: Boolean = true,
    enabled: Boolean = true,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { input ->
            val filtered = if (deleteSpaces) input.filter { !it.isWhitespace() } else input
            onValueChanged(filtered)
        },
        label = {
            Text(
                stringResource(stringResource),
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
        modifier = Modifier.fillMaxWidth(),
        colors = fieldsColors,
        shape = RoundedCornerShape(16.dp),
        leadingIcon = if (icon != null) {
            {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = Grey
                )
            }
        } else null,
        enabled = enabled
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    title: String,
    selected: String,
    onSelect: (String) -> Unit,
    values: List<String>,
    fieldsColors: TextFieldColors,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (enabled) {
                expanded = !expanded
            }
        },
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            label = {
                Text(
                    text = title,
                    color = DarkBlue,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            },
            colors = fieldsColors,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Grey)
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
                        if (enabled) {
                            onSelect(gender)
                            expanded = false
                        }
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun PasswordField(
    password: String,
    label: String,
    passwordError: String?,
    passwordVisible: MutableState<Boolean>,
    fieldsColors: TextFieldColors,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = onValueChange,
        label = {
            Text(
                label,
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
        isError = passwordError != null,
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
                    tint = if (passwordError != null) Pink else Grey
                )
            }
        },
        colors = fieldsColors,
        shape = RoundedCornerShape(16.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.pass),
                contentDescription = null,
                tint = Grey
            )
        }
    )
}