package com.stori.challenge.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.stori.challenge.domain.entities.AuthenticationState.Authenticated
import com.stori.challenge.domain.entities.AuthenticationState.Initial
import com.stori.challenge.domain.entities.AuthenticationState.Unauthenticated
import com.stori.challenge.ui.components.CustomTopBar
import com.stori.challenge.ui.events.MainEvent
import com.stori.challenge.ui.navigation.MainNavGraph
import com.stori.challenge.ui.navigation.Screen
import com.stori.challenge.ui.navigation.Screen.Home
import com.stori.challenge.ui.navigation.Screen.Login
import com.stori.challenge.ui.navigation.Screen.Register
import com.stori.challenge.ui.navigation.Screen.Splash
import com.stori.challenge.ui.navigation.Screen.Success
import com.stori.challenge.ui.navigation.UID
import com.stori.challenge.ui.theme.LocalDim
import com.stori.challenge.ui.theme.successColor
import com.stori.challenge.ui.viewmodels.MainViewModel
import com.stori.challenge.util.extensions.safeNavigate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainForm(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    updateStatusBarColor: (Color) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val navCurrentScreen = remember(navBackStackEntry) {
        Screen.allScreens.find { s -> s.route == navBackStackEntry?.destination?.route }
    } ?: Splash
    val currentScreen by rememberUpdatedState(navCurrentScreen)
    val authenticationState = mainViewModel.authenticationState.collectAsState().value
    val currentAuthState by rememberUpdatedState(authenticationState)
    val colors = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        mainViewModel.authenticationState.collect { authState ->
            if (authState != currentAuthState) {
                when (authState) {
                    is Authenticated -> navController.safeNavigate(
                        route = if (currentScreen is Register) Success.route else Home.route,
                        argument = UID to authState.uid,
                        popUpToRoute = currentScreen.route,
                    )

                    is Unauthenticated -> navController.safeNavigate(
                        route = Login.route,
                        popUpToRoute = currentScreen.route,
                    )

                    is Initial -> return@collect
                }
            }
        }
    }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.route) {
                Login.route -> updateStatusBarColor(colors.primary)
                Success.route -> updateStatusBarColor(successColor)
                Splash.route -> updateStatusBarColor(colors.secondary)
                else -> updateStatusBarColor(Color.Transparent)
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(vertical = LocalDim.current.spaceLarge),
                hostState = snackbarHostState,
            )
        },
        topBar = {
            AnimatedVisibility(visible = currentScreen.showToolbar) {
                CustomTopBar(
                    currentScreen = currentScreen,
                    scrollBehavior = scrollBehavior,
                    onBackClick = navController::navigateUp,
                    onActionClick = { mainViewModel.triggerEvent(MainEvent.ActionEvent) },
                )
            }
        },
    ) { innerPadding ->
        MainNavGraph(
            mainViewModel = mainViewModel,
            navController = navController,
            innerPadding = innerPadding,
        ) { callbackMessage ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = callbackMessage,
                    duration = SnackbarDuration.Short,
                )
            }
        }
    }
}
