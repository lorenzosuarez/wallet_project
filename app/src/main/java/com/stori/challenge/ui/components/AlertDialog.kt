package com.stori.challenge.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AlertDialog(
    dialogTitle: String,
    dialogText: String,
    confirmButtonText: String,
    dismissButtonText: String,
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val confirmAndDismiss = {
        onConfirmation()
        onDismissRequest()
    }

    AlertDialog(
        title = {
            Text(text = dialogTitle, style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Text(text = dialogText, style = MaterialTheme.typography.titleMedium)
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.errorContainer,
                ),
                onClick = confirmAndDismiss,
                content = { Text(confirmButtonText) },
            )
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                content = { Text(dismissButtonText) },
            )
        },
    )
}
