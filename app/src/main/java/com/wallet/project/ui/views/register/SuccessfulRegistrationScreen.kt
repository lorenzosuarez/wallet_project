package com.wallet.project.ui.views.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.wallet.project.R
import com.wallet.project.ui.components.CustomButton
import com.wallet.project.ui.navigation.Screen
import com.wallet.project.ui.navigation.UID
import com.wallet.project.ui.theme.LocalDim
import com.wallet.project.ui.theme.successColor
import com.wallet.project.util.extensions.safeNavigate

@Composable
fun SuccessfulRegistrationScreen(navController: NavHostController) {
    val dimensions = LocalDim.current
    val uid: String = remember(Unit) {
        navController.currentBackStackEntry?.arguments?.getString(UID).orEmpty()
    }
    val doneLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.done_animation,
        ),
    )
    val doneLottie by animateLottieCompositionAsState(
        doneLottieComposition,
        iterations = 1,
        isPlaying = true,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(successColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.padding(vertical = dimensions.paddingLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensions.paddingLarge),
        ) {
            Text(
                text = stringResource(id = R.string.success),
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                ),
            )
            Text(
                text = stringResource(id = R.string.account_created),
                maxLines = 1,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                ),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            LottieAnimation(
                composition = doneLottieComposition,
                progress = doneLottie,
            )
        }
        CustomButton(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(vertical = dimensions.paddingLarge),
            buttonText = stringResource(id = R.string.button_continue),
            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
        ) {
            navController.safeNavigate(
                route = Screen.Home.route,
                argument = UID to uid,
                popUpToRoute = Screen.Success.route,
            )
        }
    }
}
