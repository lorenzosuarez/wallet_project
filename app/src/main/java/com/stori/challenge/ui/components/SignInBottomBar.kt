package com.stori.challenge.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stori.challenge.ui.viewmodels.MainViewModel

@Composable
fun SignInBottomBar(mainViewModel: MainViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        CustomButton(
            modifier = Modifier.fillMaxWidth(0.85f),
            isLoading = false,
            buttonText = "Register",
        ) {}
    }
}
