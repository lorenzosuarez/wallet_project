package com.stori.challenge.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.stori.challenge.R
import com.stori.challenge.ui.states.UiState
import com.stori.challenge.ui.theme.LocalDim

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    state: UiState,
    errorMessage: String = "",
    onValueChange: (String) -> Unit,
    placeholderText: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val dimensions = LocalDim.current
    Column {
        OutlinedTextField(
            modifier = modifier.heightIn(min = dimensions.textFieldHeight),
            value = value,
            shape = MaterialTheme.shapes.medium,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            trailingIcon = {
                Row(
                    modifier = Modifier.padding(end = dimensions.spaceSmall),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    trailingIcon?.invoke() ?: run {
                        when (state) {
                            UiState.Idle -> Icon(
                                modifier = Modifier.size(25.dp),
                                painter = painterResource(id = R.drawable.ic_circle_checked),
                                contentDescription = state.name,
                            )
                            UiState.Error -> Icon(
                                modifier = Modifier.size(25.dp),
                                painter = painterResource(id = R.drawable.ic_warning),
                                contentDescription = state.name,
                            )

                            else -> Unit
                        }
                    }
                }
            },
            isError = state.isError,
            placeholder = {
                Text(
                    text = placeholderText,
                    style = MaterialTheme.typography.labelLarge,
                )
            },
            maxLines = 1,
            singleLine = true,
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                unfocusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
                focusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
                errorContainerColor = MaterialTheme.colorScheme.outlineVariant,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
            ),
            textStyle = MaterialTheme.typography.labelLarge,
        )
        AnimatedVisibility(visible = state.isError && errorMessage.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(horizontal = dimensions.spaceSmall),
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}
