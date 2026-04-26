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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dentapp.R
import com.example.dentapp.presentation.auth.viewmodel.SignInViewModel
import com.example.dentapp.ui.theme.Blue
import com.example.dentapp.ui.theme.DarkBlue
import com.example.dentapp.ui.theme.Grey
import com.example.dentapp.ui.theme.GreyBlue
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.Pink
import com.example.dentapp.ui.theme.VeryDarkBlue
import com.example.dentapp.ui.theme.White

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onSignInSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isEmailLoading by viewModel.isEmailLoading.collectAsState()
    val isGoogleLoading by viewModel.isGoogleLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    val passwordVisible = rememberSaveable { mutableStateOf(false) }

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
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
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

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.sign_in_descr),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = GreyBlue
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = !isEmailLoading && !isGoogleLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = VeryDarkBlue,
                    disabledContainerColor = Color.White.copy(alpha = 0.7f),
                    disabledContentColor = VeryDarkBlue
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
                            painter = painterResource(id = R.drawable.no_signal),
                            contentDescription = stringResource(R.string.google),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Работать автономно",
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
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
                    fontSize = 14.sp,
                    color = VeryDarkBlue
                )
                Divider(
                    modifier = Modifier.weight(1f),
                    color = VeryDarkBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            InputTextField(email, R.string.email, fieldsColors, R.drawable.post) { viewModel.updateEmail(it) }

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                password = password,
                label = stringResource(R.string.password),
                passwordError = passwordError,
                passwordVisible = passwordVisible,
                fieldsColors = fieldsColors
            ) { viewModel.updatePassword(it) }

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
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
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
                    disabledContainerColor = Blue.copy(alpha = 0.5f),
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
                    text = stringResource(R.string.no_account),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = VeryDarkBlue
                )
                Text(
                    text = stringResource(R.string.do_sign_up),
                    fontFamily = Montserrat,
                    color = VeryDarkBlue,
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onNavigateToSignUp() }
                )
            }
        }
    }
}