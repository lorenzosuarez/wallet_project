package com.stori.challenge.ui.components

import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.stori.challenge.R
import com.stori.challenge.ui.states.UiState
import com.stori.challenge.ui.theme.StoriTheme

/**
 * A custom circular profile image component with an optional camera icon button overlay.
 *
 * @param bitmap The [Bitmap] to be displayed as the profile image. If null, a default image will be shown.
 * @param onClick A lambda function to handle click events on the camera icon button.
 *
 * @sample ProfileImagePreview
 */
@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    state: UiState = UiState.None,
    bitmap: Bitmap? = null,
    onClick: () -> Unit = {},
) {
    val color = MaterialTheme.colorScheme
    val context = LocalContext.current

    Box(modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.background)
                .border(
                    width = 2.5.dp,
                    color = if (state.isError) color.error else color.onSecondaryContainer,
                    shape = CircleShape,
                )
                .padding(all = 1.dp),
            contentAlignment = Alignment.Center,
        ) {
            val imageRequest = ImageRequest.Builder(context)
                .data(bitmap ?: R.drawable.ic_stori)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .transformations(CircleCropTransformation())
                .build()
            val imageLoader = ImageLoader.Builder(context).respectCacheHeaders(false).build()

            Image(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                painter = rememberAsyncImagePainter(imageRequest, imageLoader),
                contentDescription = null,
            )
        }
        /*IconButton(
            onClick = onClick,
            modifier = Modifier
                .padding(all = 1.dp)
                .clip(CircleShape)
                .size(40.dp)
                .align(Alignment.BottomEnd),
            colors = IconButtonDefaults.iconButtonColors(containerColor = color.primary),
        ) {
            AnimatedVisibility(visible = ) {

            }
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(5.dp),
                tint = color.inversePrimary,
            )
        }*/
    }
}

@Composable
@Preview(showBackground = true, name = "Profile Image Preview")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProfileImagePreview() {
    StoriTheme {
        ProfileImage(
            bitmap = null,
        ) {}
    }
}
