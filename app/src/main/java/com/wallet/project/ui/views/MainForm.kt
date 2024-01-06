package com.wallet.project.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.wallet.project.R
import com.wallet.project.domain.entities.AuthenticationState.Authenticated
import com.wallet.project.domain.entities.AuthenticationState.Initial
import com.wallet.project.domain.entities.AuthenticationState.Unauthenticated
import com.wallet.project.ui.components.CustomTopBar
import com.wallet.project.ui.events.MainEvent
import com.wallet.project.ui.navigation.MainNavGraph
import com.wallet.project.ui.navigation.Screen
import com.wallet.project.ui.navigation.Screen.Home
import com.wallet.project.ui.navigation.Screen.Login
import com.wallet.project.ui.navigation.Screen.Register
import com.wallet.project.ui.navigation.Screen.Splash
import com.wallet.project.ui.navigation.Screen.Success
import com.wallet.project.ui.navigation.UID
import com.wallet.project.ui.theme.Dimensions
import com.wallet.project.ui.theme.LocalDim
import com.wallet.project.ui.theme.successColor
import com.wallet.project.ui.viewmodels.MainViewModel
import com.wallet.project.util.extensions.safeNavigate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainForm(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    updateStatusBarColor: (Color, Boolean) -> Unit,
) {
    val dimensions = LocalDim.current
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
    val darkIcons = isSystemInDarkTheme()

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
                Login.route -> updateStatusBarColor(colors.primary, darkIcons)

                Success.route -> updateStatusBarColor(successColor, darkIcons)

                Splash.route -> updateStatusBarColor(colors.secondary, darkIcons)

                else -> updateStatusBarColor(Color.Transparent, !darkIcons)
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            if (currentScreen is Home) {
                ExtendedFloatingActionButton(
                    modifier = Modifier.padding(all = dimensions.spaceMedium),
                    containerColor = MaterialTheme.colorScheme.inversePrimary,
                    onClick = { /* */ },
                    text = {
                        Text(
                            text = stringResource(id = R.string.pay),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 19.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp,
                            ),
                        )
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_nfc),
                            contentDescription = "nfc",
                        )
                    }
                )
            }
        },
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
