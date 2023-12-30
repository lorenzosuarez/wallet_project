package com.wallet.project.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wallet.project.ui.theme.WalletTheme

/**
 * Creates a shimmer effect brush that can be applied to composables.
 *
 * @param showShimmer A flag to control the visibility of the shimmer effect.
 * @param targetValue The target value of the animation which determines the width of the shimmer effect.
 * @return A [Brush] that gives a shimmer effect.
 */
@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800),
                repeatMode = RepeatMode.Reverse,
            ),
            label = "",
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value),
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero,
        )
    }
}

/**
 * Wraps content with a shimmer effect overlay when `showShimmer` is true.
 *
 * @param modifier Modifier to be applied to the shimmer container.
 * @param showShimmer A flag to control the visibility of the shimmer effect.
 * @param shape The shape of the shimmer overlay.
 * @param content A composable that will be the underlying content over which the shimmer effect is shown.
 * @author Lorenzo Suarez
 */

@Composable
fun ShimmerContent(
    modifier: Modifier = Modifier,
    showShimmer: Boolean = true,
    shape: Shape = RectangleShape,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier) {
        content()
        if (showShimmer) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(shape)
                    .background(shimmerBrush(targetValue = 1300f, showShimmer = true)),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShimmerContent() {
    WalletTheme {
        ShimmerContent(
            modifier = Modifier.size(200.dp),
            showShimmer = true,
            shape = RectangleShape,
        ) {
            Box(modifier = Modifier.background(Color.Gray).fillMaxSize())
        }
    }
}
