package com.stori.challenge.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.stori.challenge.ui.theme.StoriTheme
import com.stori.challenge.ui.viewmodels.MainViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val mainViewModel: MainViewModel = koinViewModel()

            StoriTheme {
                MainForm(
                    mainViewModel = mainViewModel,
                )
            }
        }
    }
}
