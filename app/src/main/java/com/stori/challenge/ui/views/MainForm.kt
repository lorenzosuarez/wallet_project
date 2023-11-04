package com.stori.challenge.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stori.challenge.R
import com.stori.challenge.domain.entities.AuthenticationState.Authenticated
import com.stori.challenge.domain.entities.AuthenticationState.Initial
import com.stori.challenge.domain.entities.AuthenticationState.Unauthenticated
import com.stori.challenge.ui.events.MainEvent
import com.stori.challenge.ui.navigation.MainNavGraph
import com.stori.challenge.ui.navigation.Screen
import com.stori.challenge.ui.navigation.Screen.Companion.hasActionIcon
import com.stori.challenge.ui.navigation.Screen.Home
import com.stori.challenge.ui.navigation.Screen.Login
import com.stori.challenge.ui.navigation.Screen.Register
import com.stori.challenge.ui.navigation.Screen.Splash
import com.stori.challenge.ui.navigation.Screen.Success
import com.stori.challenge.ui.navigation.UID
import com.stori.challenge.ui.theme.LocalDim
import com.stori.challenge.ui.viewmodels.MainViewModel
import com.stori.challenge.util.extensions.safeNavigate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainForm(mainViewModel: MainViewModel) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val navCurrentScreen = remember(navBackStackEntry) {
        Screen.allScreens.find { s -> s.route == navBackStackEntry?.destination?.route }
    } ?: Splash
    val currentScreen by rememberUpdatedState(navCurrentScreen)
    val authenticationState = mainViewModel.authenticationState.collectAsState().value
    val currentAuthState by rememberUpdatedState(authenticationState)

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
                MediumTopAppBar(
                    title = {
                        Text(
                            modifier = Modifier.padding(horizontal = LocalDim.current.paddingSmall),
                            text = currentScreen.resTitle?.let { title -> stringResource(id = title) }
                                .orEmpty(),
                            maxLines = 1,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 30.sp,
                            ),
                        )
                    },
                    navigationIcon = {
                        AnimatedVisibility(visible = currentScreen.showBack) {
                            IconButton(onClick = navController::navigateUp) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_back),
                                    contentDescription = "back",
                                    tint = MaterialTheme.colorScheme.inversePrimary,
                                )
                            }
                        }
                    },
                    actions = {
                        AnimatedVisibility(visible = currentScreen.hasActionIcon) {
                            IconButton(onClick = { mainViewModel.triggerEvent(MainEvent.ActionEvent) }) {
                                currentScreen.actionIcon?.let { actionIcon ->
                                    Icon(
                                        painter = painterResource(id = actionIcon),
                                        contentDescription = "actionIcon",
                                    )
                                }
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior,
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
