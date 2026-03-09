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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dentapp.R
import com.example.dentapp.presentation.auth.viewmodel.SignInViewModel
import com.example.dentapp.ui.theme.Blue
import com.example.dentapp.ui.theme.DarkGrey
import com.example.dentapp.ui.theme.GreyBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.NewBlue
import com.example.dentapp.ui.theme.Pink
import com.example.dentapp.ui.theme.VeryDarkBlue
import com.example.dentapp.ui.theme.White

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onSignInSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onGoogleSignIn: () -> Unit
) {
    val email  by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isEmailLoading by viewModel.isEmailLoading.collectAsState()
    val isGoogleLoading by viewModel.isGoogleLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    val passwordVisible = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            // scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

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
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
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
                Image(
                    painter = painterResource(id = R.drawable.auth_logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.7f)
                        .drawWithContent { drawContent() }
                )

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = stringResource(R.string.sign_in_descr),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = GreyBlue
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        onGoogleSignIn()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = !isEmailLoading && !isGoogleLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = VeryDarkBlue,
                        disabledContainerColor = Color.White.copy(alpha = 0.7f),
                        disabledContentColor = VeryDarkBlue.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    if (isGoogleLoading) {
                        CircularProgressIndicator(
                            color = VeryDarkBlue,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.google_icon),
                                contentDescription = stringResource(R.string.google),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(R.string.google_sign),
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp,
                                color = VeryDarkBlue
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = VeryDarkBlue
                    )
                    Text(
                        text = stringResource(R.string.or),
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = VeryDarkBlue
                    )
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = VeryDarkBlue
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { viewModel.updateEmail(it) },
                    label = {
                        Text(
                            stringResource(R.string.email),
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = DarkGrey
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
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
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.post),
                            contentDescription = null,
                            tint = DarkGrey
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

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
                                tint = if (passwordError != null) Pink else DarkGrey
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
                        errorIndicatorColor = Pink,
                        errorLabelColor = Pink,
                        focusedTextColor = DarkGrey,
                        unfocusedTextColor = DarkGrey
                    ),
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.pass),
                            contentDescription = null,
                            tint = DarkGrey
                        )
                    }
                )

                passwordError?.let { error ->
                    Text(
                        text = error,
                        color = Pink,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                errorMessage?.let { message ->
                    Text(
                        text = message,
                        color = Pink,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.signIn(onSignInSuccess) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = !isEmailLoading && !isGoogleLoading &&
                            email.isNotEmpty() && password.isNotEmpty() &&
                            passwordError == null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue,
                        contentColor = Color.White,
                        disabledContainerColor = Blue,
                        disabledContentColor = Color.White.copy(alpha = 0.7f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    if (isEmailLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.do_sign_in),
                            style = MaterialTheme.typography.titleMedium,
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
                        text = stringResource(R.string.no_account),
                        style = MaterialTheme.typography.bodyMedium,
                        color = VeryDarkBlue
                    )
                    Text(
                        text = stringResource(R.string.do_sign_up),
                        style = MaterialTheme.typography.bodyMedium,
                        color = VeryDarkBlue,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onNavigateToSignUp() }
                    )
                }
            }
        }
    }
}