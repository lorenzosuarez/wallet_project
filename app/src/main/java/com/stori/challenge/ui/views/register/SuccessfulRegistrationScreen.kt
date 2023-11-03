package com.stori.challenge.ui.views.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stori.challenge.R
import com.stori.challenge.ui.components.CustomButton
import com.stori.challenge.ui.navigation.Screen
import com.stori.challenge.ui.navigation.UID
import com.stori.challenge.ui.theme.LocalDim
import com.stori.challenge.ui.theme.storiDarkInverseSurface
import com.stori.challenge.util.extensions.safeNavigate

@Composable
fun SuccessfulRegistrationScreen(navController: NavHostController) {
    val dimensions = LocalDim.current
    val uid: String = remember(Unit) {
        navController.currentBackStackEntry?.arguments?.getString(UID).orEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(storiDarkInverseSurface),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensions.paddingLarge),
            ) {
                Icon(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(id = R.drawable.ic_done),
                    tint = Color.White,
                    contentDescription = "done",
                )
                Text(
                    text = "Success!",
                    maxLines = 1,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 3.sp,
                    ),
                )
                Text(
                    text = "Your account has been created",
                    maxLines = 1,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                    ),
                )
            }
        }

        CustomButton(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(vertical = dimensions.paddingLarge),
            buttonText = "Continue",
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