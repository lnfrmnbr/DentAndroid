package com.example.dentapp.presentation.auth.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dentapp.R
import com.example.dentapp.presentation.auth.viewmodel.SignUpViewModel
import com.example.dentapp.ui.theme.DarkCyan

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

    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val confirmPasswordVisible = rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkCyan)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    InputTextField(email, R.string.email) { viewModel.updateEmail(it) }

                    InputTextField(lastName, R.string.lastName) { viewModel.updateLastName(it) }

                    InputTextField(firstName, R.string.name) { viewModel.updateFirstName(it) }

                    InputTextField(patronymic, R.string.patronymic) { viewModel.updatePatronymic(it) }

                    GenderDropdown(
                        selectedGender = sex,
                        onGenderSelected = { viewModel.updateSex(it) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { viewModel.updatePassword(it) },
                        label = {
                            Text(
                                stringResource(R.string.password),
                                color = DarkCyan.copy(alpha = 0.6f)
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
                                    tint = DarkCyan.copy(alpha = 0.6f)
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedLabelColor = DarkCyan,
                            unfocusedLabelColor = DarkCyan.copy(alpha = 0.6f),
                            focusedIndicatorColor = DarkCyan,
                            unfocusedIndicatorColor = DarkCyan.copy(alpha = 0.3f),
                            focusedTextColor = DarkCyan.copy(alpha = 0.6f),
                            unfocusedTextColor = DarkCyan.copy(alpha = 0.6f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { viewModel.updateConfirmPassword(it) },
                        label = {
                            Text(
                                stringResource(R.string.submit_password),
                                color = DarkCyan.copy(alpha = 0.6f)
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
                                    tint = DarkCyan.copy(alpha = 0.6f)
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedLabelColor = DarkCyan,
                            unfocusedLabelColor = DarkCyan.copy(alpha = 0.6f),
                            focusedIndicatorColor = DarkCyan,
                            unfocusedIndicatorColor = DarkCyan.copy(alpha = 0.3f),
                            focusedTextColor = DarkCyan.copy(alpha = 0.6f),
                            unfocusedTextColor = DarkCyan.copy(alpha = 0.6f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )

                    errorMessage?.let { message ->
                        Text(
                            text = message,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    Button(
                        onClick = { viewModel.signUp(onSignUpSuccess) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled = !isLoading && email.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkCyan,
                            contentColor = Color.White,
                            disabledContainerColor = DarkCyan.copy(alpha = 0.5f),
                            disabledContentColor = Color.White.copy(alpha = 0.7f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.do_sign_up),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.have_account),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = stringResource(R.string.do_sign_in),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
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
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(stringResource(stringResource), color = DarkCyan.copy(alpha = 0.6f)) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = DarkCyan,
            unfocusedLabelColor = DarkCyan.copy(alpha = 0.6f),
            focusedIndicatorColor = DarkCyan,
            unfocusedIndicatorColor = DarkCyan.copy(alpha = 0.3f),
            focusedTextColor = DarkCyan.copy(alpha = 0.6f),
            unfocusedTextColor = DarkCyan.copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderDropdown(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val genders = listOf(
        stringResource(R.string.gender_male),
        stringResource(R.string.gender_female)
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedGender,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            label = {
                Text(
                    text = stringResource(R.string.gender),
                    color = DarkCyan.copy(alpha = 0.6f)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLabelColor = DarkCyan,
                unfocusedLabelColor = DarkCyan.copy(alpha = 0.6f),
                focusedIndicatorColor = DarkCyan,
                unfocusedIndicatorColor = DarkCyan.copy(alpha = 0.3f),
                focusedTextColor = DarkCyan,
                unfocusedTextColor = DarkCyan.copy(alpha = 0.6f),
                disabledTextColor = DarkCyan.copy(alpha = 0.6f),
                disabledContainerColor = Color.White,
                disabledIndicatorColor = DarkCyan.copy(alpha = 0.3f),
                disabledLabelColor = DarkCyan.copy(alpha = 0.6f)
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(DarkCyan)
        ) {
            genders.forEach { gender ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = gender,
                            color = Color.White
                        )
                    },
                    onClick = {
                        onGenderSelected(gender)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}