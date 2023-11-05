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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stori.challenge.R
import com.stori.challenge.ui.states.UiState
import com.stori.challenge.ui.theme.LocalDim
import com.stori.challenge.ui.theme.StoriTheme

/**
 * A custom TextField with an optional trailing icon that changes based on the UI state.
 * It can display an error message underneath the TextField when the state is an error.
 *
 * @param modifier Modifier to be applied to the TextField.
 * @param value The current text to be displayed in the TextField.
 * @param state The UI state that the TextField reflects.
 * @param errorMessage The error message to display when the state is [UiState.Error].
 * @param onValueChange Called when the input service updates the text.
 * @param placeholderText The text to be displayed when the input is empty.
 * @param visualTransformation The VisualTransformation to apply to the text input.
 * @param keyboardOptions Configuration options for the software keyboard.
 * @param trailingIcon The optional trailing composable to display inside the TextField.
 *
 * @sample PreviewCustomTextField
 * @author Lorenzo Suarez
 */
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

@Preview(showBackground = true)
@Composable
fun PreviewCustomTextField() {
    StoriTheme {
        CustomTextField(
            value = "Sample Text",
            state = UiState.Idle,
            errorMessage = "Error message",
            onValueChange = {},
        )
    }
}
