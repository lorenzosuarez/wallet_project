package com.stori.challenge.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun LogoutAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                    onConfirmation()
                },
            ) {
                Text("Log out")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
            ) {
                Text("Cancel")
            }
        },
    )
}
