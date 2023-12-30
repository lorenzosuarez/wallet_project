package com.wallet.project.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wallet.project.ui.theme.LocalDim
import com.wallet.project.ui.theme.WalletTheme

/**
 * A custom button that can show a loading indicator and an optional icon alongside the text.
 *
 * @param modifier The modifier to be applied to the Button.
 * @param isLoading The Boolean flag to show a loading indicator instead of the button text and icon.
 * @param buttonText The text to display on the button.
 * @param imageVector The optional ImageVector to display an icon alongside the text.
 * @param onClick The lambda to be invoked when the button is pressed.
 *
 * @sample PreviewCustomButton
 * @author Lorenzo Suarez
 */
@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    buttonText: String,
    imageVector: ImageVector? = null,
    onClick: () -> Unit,
) {
    val dimensions = LocalDim.current

    Button(
        modifier = modifier,
        enabled = !isLoading,
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(dimensions.paddingLarge),
        onClick = onClick,
    ) {
        AnimatedVisibility(isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = MaterialTheme.colorScheme.onTertiary,
                strokeWidth = 2.dp,
            )
        }
        AnimatedVisibility(!isLoading) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensions.spaceMedium),
            ) {
                Text(
                    text = buttonText.uppercase(),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 19.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp,
                    ),
                )
                imageVector?.let { img ->
                    Icon(imageVector = img, contentDescription = img.name)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomButton() {
    WalletTheme {
        CustomButton(
            buttonText = "Click Me",
            onClick = {},
        )
    }
}
