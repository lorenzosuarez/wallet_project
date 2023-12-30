package com.wallet.project.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class ShapeType {
    WAVY,
    INVERTED_WAVY,
    SEMI_CIRCLE,
}

@Composable
fun CustomShape(
    modifier: Modifier = Modifier,
    shapeColor: Color = Color.Blue,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    shapeType: ShapeType = ShapeType.WAVY,
) {
    Box(
        modifier = modifier.background(shapeColor),
    ) {
        Canvas(modifier = modifier.matchParentSize()) {
            val width = size.width
            val height = size.height
            val curveHeight = height * 0.25f
            val path = Path().apply {
                when (shapeType) {
                    ShapeType.WAVY -> {
                        moveTo(0f, height - curveHeight)
                        cubicTo(
                            width * 0.33f,
                            height + curveHeight * 0.5f,
                            width * 0.66f,
                            height - curveHeight * 1.9f,
                            width,
                            height - curveHeight,
                        )
                    }

                    ShapeType.INVERTED_WAVY -> {
                        moveTo(0f, curveHeight)
                        cubicTo(
                            width * 0.33f,
                            -curveHeight * 0.5f,
                            width * 0.66f,
                            curveHeight * 1.9f,
                            width,
                            curveHeight,
                        )
                    }

                    ShapeType.SEMI_CIRCLE -> {
                        val arcRect = Rect(0f, 450f, width, height * 0.9f)
                        moveTo(0f, height)
                        arcTo(arcRect, -180f, -180f, false)
                    }
                }
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            drawPath(path, backgroundColor)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WavyShapePreview() {
    Box(Modifier.fillMaxSize()) {
        CustomShape(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            shapeColor = Color.Blue,
            shapeType = ShapeType.SEMI_CIRCLE,
        )
    }
}
