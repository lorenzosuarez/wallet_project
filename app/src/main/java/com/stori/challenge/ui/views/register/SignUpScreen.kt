package com.stori.challenge.ui.views.register

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.stori.challenge.R
import com.stori.challenge.ui.components.CustomButton
import com.stori.challenge.ui.components.CustomTextField
import com.stori.challenge.ui.components.DocumentCard
import com.stori.challenge.ui.events.RegisterEvent
import com.stori.challenge.ui.states.MainResultSate
import com.stori.challenge.ui.theme.LocalDim
import com.stori.challenge.ui.viewmodels.RegisterViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.scan
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SignUpScreen(
    registerViewModel: RegisterViewModel = getViewModel(),
    callBackMessage: (String) -> Unit,
) {
    val context = LocalContext.current
    val dimensions = LocalDim.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val registerState = registerViewModel.registerState
    val name = registerState.name
    val lastName = registerState.lastName
    val password = registerState.password
    val email = registerState.email

    val documentState = registerState.documentState
    val emailState = registerState.emailState
    val passwordState = registerState.passwordState
    val nameState = registerState.nameState
    val lastNameState = registerState.lastNameState

    val isLoading = registerState.isLoading
    val isVisiblePassword = registerState.isVisiblePassword
    val registerResultState = registerViewModel.registerResultState

    val takePicture = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = registerViewModel::setImageBitmap,
    )

    LaunchedEffect(Unit) {
        registerResultState.collect { state ->
            when (state) {
                is MainResultSate.Failure -> {
                    callBackMessage(
                        state.errorMessage ?: context.getString(R.string.error_login),
                    )
                }

                else -> Unit
            }
        }
    }

    LaunchedEffect(cameraPermissionState.status) {
        snapshotFlow { cameraPermissionState.status }
            .scan(
                Pair<PermissionStatus?, PermissionStatus?>(
                    null,
                    null,
                ),
            ) { previousPair, newStatus ->
                Pair(previousPair.second, newStatus)
            }
            .filter { (previousStatus, currentStatus) ->
                previousStatus is PermissionStatus.Denied && currentStatus == PermissionStatus.Granted
            }
            .collect {
                takePicture.launch()
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensions.spaceMedium),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(vertical = dimensions.paddingLarge),
            textAlign = TextAlign.Start,
            text = "Please fill the input below here",
            maxLines = 1,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
            ),
        )

        DocumentCard(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight(),
            state = documentState,
            text = "Foto de ID",
        ) {
            if (!cameraPermissionState.status.isGranted) {
                cameraPermissionState.launchPermissionRequest()
            } else {
                takePicture.launch()
            }
        }

        CustomTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            state = emailState,
            value = email,
            onValueChange = { newValue ->
                registerViewModel.onEvent(
                    RegisterEvent.EmailChanged(email = newValue),
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            placeholderText = "Email",
        )

        CustomTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            state = passwordState,
            value = password,
            onValueChange = { newValue ->
                registerViewModel.onEvent(
                    RegisterEvent.PasswordChanged(
                        password = newValue,
                    ),
                )
            },
            placeholderText = "Password",
            visualTransformation = if (isVisiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
            ),
            trailingIcon = {
                IconToggleButton(
                    checked = isVisiblePassword,
                    onCheckedChange = { visible ->
                        registerViewModel.onEvent(
                            RegisterEvent.VisiblePassword(
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
            errorMessage = "Debe tener un minimo de 6",
        )

        CustomTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            state = nameState,
            value = name,
            onValueChange = { newValue ->
                registerViewModel.onEvent(
                    RegisterEvent.NameChanged(name = newValue),
                )
            },
            placeholderText = "Name",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words,
            ),
        )

        CustomTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            state = lastNameState,
            value = lastName,
            onValueChange = { newValue ->
                registerViewModel.onEvent(
                    RegisterEvent.LastNameChanged(lastName = newValue),
                )
            },
            placeholderText = "Lastname",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words,
            ),
        )

        Spacer(Modifier.height(dimensions.spaceSmall))

        CustomButton(
            modifier = Modifier.fillMaxWidth(0.85f),
            isLoading = isLoading,
            buttonText = "Register",
            onClick = registerViewModel::register,
        )
    }
}
