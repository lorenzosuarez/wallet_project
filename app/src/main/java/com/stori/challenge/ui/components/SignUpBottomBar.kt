package com.stori.challenge.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stori.challenge.ui.events.MainEvent
import com.stori.challenge.ui.viewmodels.MainViewModel

@Composable
fun SignUpBottomBar(mainViewModel: MainViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        CustomButton(
            modifier = Modifier.fillMaxWidth(0.85f),
            isLoading = false,
            buttonText = "Sign in",
        ) {
            mainViewModel.triggerEvent(
                MainEvent.ActionEvent,
            )
        }

        CustomButton(
            modifier = Modifier.fillMaxWidth(0.85f),
            isLoading = false,
            buttonText = "Register",
        ) {}
    }
}
