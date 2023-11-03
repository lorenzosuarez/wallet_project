package com.stori.challenge.ui.components

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
import androidx.compose.ui.unit.dp
import com.stori.challenge.ui.theme.LocalDim

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
                    text = buttonText,
                    style = MaterialTheme.typography.labelLarge,
                )
                imageVector?.let { img ->
                    Icon(imageVector = img, contentDescription = img.name)
                }
            }
        }
    }
}
