package com.wallet.project.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wallet.project.ui.theme.WalletTheme

/**
 * Displays an AlertDialog with customizable title, text, and buttons for confirmation and dismissal.
 *
 * @param dialogTitle The text to be used as the title of the alert dialog.
 * @param dialogText The text to be used for the message of the alert dialog.
 * @param confirmButtonText The text to be used on the confirmation button.
 * @param dismissButtonText The text to be used on the dismiss button.
 * @param onConfirmation The lambda to be invoked when the confirmation button is pressed.
 * @param onDismissRequest The lambda to be invoked when the dialog is dismissed.
 *
 * @sample PreviewAlertDialog
 * @author Lorenzo Suarez
 */

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

@Preview(showBackground = true)
@Composable
fun PreviewAlertDialog() {
    WalletTheme {
        AlertDialog(
            dialogTitle = "Sample Title",
            dialogText = "This is a sample alert dialog showing how it will look.",
            confirmButtonText = "Confirm",
            dismissButtonText = "Dismiss",
            onConfirmation = {},
            onDismissRequest = {},
        )
    }
}
