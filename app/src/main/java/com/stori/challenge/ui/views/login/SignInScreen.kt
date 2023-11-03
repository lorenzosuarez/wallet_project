package com.stori.challenge.ui.views.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stori.challenge.R
import com.stori.challenge.ui.components.CustomButton
import com.stori.challenge.ui.components.CustomTextField
import com.stori.challenge.ui.events.LoginEvent
import com.stori.challenge.ui.navigation.Screen
import com.stori.challenge.ui.states.MainResultSate
import com.stori.challenge.ui.theme.LocalDim
import com.stori.challenge.ui.viewmodels.LoginViewModel
import com.stori.challenge.ui.viewmodels.MainViewModel
import com.stori.challenge.util.extensions.debounceClickable
import com.stori.challenge.util.extensions.safeNavigate
import org.koin.androidx.compose.getViewModel

@Composable
fun SignInScreen(
    mainViewModel: MainViewModel,
    loginViewModel: LoginViewModel = getViewModel(),
    navController: NavHostController,
    callBack: (String) -> Unit = {},
) {
    val context = LocalContext.current
    val dimensions = LocalDim.current
    val loginState = loginViewModel.loginState
    val password = loginState.password
    val email = loginState.email
    val isVisiblePassword = loginState.isVisiblePassword
    val isLoading = loginState.isLoading
    val passwordState = loginState.passwordState
    val emailState = loginState.emailState
    val loginResultSate = loginViewModel.loginResultSate

    LaunchedEffect(Unit) {
        loginResultSate.collect { state ->
            when (state) {
                is MainResultSate.Failure -> {
                    callBack(context.getString(R.string.error_login))
                }

                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensions.spaceMedium),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(R.drawable.ic_stori),
                    contentDescription = "stori_logo",
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .offset(y = (-10).dp)
                    .background(MaterialTheme.colorScheme.secondary),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = dimensions.largeRounded,
                                topEnd = dimensions.largeRounded,
                                bottomStart = dimensions.notRounded,
                                bottomEnd = dimensions.notRounded,
                            ),
                        )
                        .background(MaterialTheme.colorScheme.background),
                )
            }
        }

        Text(
            modifier = Modifier.fillMaxWidth(0.85f),
            textAlign = TextAlign.Start,
            text = "Login",
            maxLines = 1,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 25.sp,
            ),
        )

        CustomTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            state = emailState,
            value = email,
            onValueChange = { newValue ->
                loginViewModel.onEvent(
                    LoginEvent.EmailChanged(email = newValue),
                )
            },
            placeholderText = "Email",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
        )

        CustomTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            state = passwordState,
            value = password,
            onValueChange = { newValue ->
                loginViewModel.onEvent(
                    LoginEvent.PasswordChanged(
                        password = newValue,
                    ),
                )
            },
            placeholderText = "Password",
            visualTransformation = if (isVisiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            errorMessage = "Debe tener un minimo de 6",
            trailingIcon = {
                IconToggleButton(
                    checked = isVisiblePassword,
                    onCheckedChange = { visible ->
                        loginViewModel.onEvent(
                            LoginEvent.VisiblePassword(
                                isVisiblePassword = visible,
                            ),
                        )
                    },
                ) {
                    Icon(
                        painter = painterResource(id = if (!isVisiblePassword) R.drawable.ic_open_eye else R.drawable.ic_eye_close),
                        contentDescription = "Toggle password visibility",
                    )
                }
            },
        )

        Row(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_checked),
                contentDescription = "session",
                tint = MaterialTheme.colorScheme.outlineVariant,
            )
            Spacer(Modifier.width(dimensions.spaceXXSmall))
            Text(
                text = "keep me sign in",
                style = MaterialTheme.typography.labelLarge,
            )
        }

        CustomButton(
            modifier = Modifier
                .fillMaxWidth(0.85f),
            isLoading = isLoading,
            buttonText = "Sign in",
            onClick = loginViewModel::login,
        )

        Row(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "You are new? ",
                style = MaterialTheme.typography.labelLarge,
            )

            Text(
                modifier = Modifier.debounceClickable {
                    navController.safeNavigate(
                        route = Screen.Register.route,
                    )
                },
                text = "Sign up",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.outline,
                textDecoration = TextDecoration.Underline,
            )
        }
    }
}
