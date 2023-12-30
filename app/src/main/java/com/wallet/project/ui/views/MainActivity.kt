package com.wallet.project.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.wallet.project.ui.theme.WalletTheme
import com.wallet.project.ui.viewmodels.MainViewModel
import com.wallet.project.util.extensions.setStatusBarColor
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            WalletTheme {
                MainForm(
                    mainViewModel = mainViewModel,
                    navController = rememberNavController(),
                ) { color, darkIcons ->
                    setStatusBarColor(
                        color = color,
                        darkIcons = darkIcons,
                    )
                }
            }
        }
    }
}
