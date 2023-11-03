package com.stori.challenge.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun DottedDivider(
    color: Color = MaterialTheme.colorScheme.outline,
    strokeWidth: Float = 1f,
    dashLength: Float = 10f,
    dashGap: Float = 10f,
) {
    Canvas(modifier = Modifier.height(strokeWidth.dp).fillMaxWidth()) {
        drawLine(
            color = color,
            start = Offset(0f, center.y),
            end = Offset(size.width, center.y),
            strokeWidth = strokeWidth,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, dashGap), 0f),
            cap = StrokeCap.Round,
        )
    }
}
