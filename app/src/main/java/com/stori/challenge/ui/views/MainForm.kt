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
import com.stori.challenge.ui.navigation.UID
import com.stori.challenge.ui.theme.LocalDim
import com.stori.challenge.ui.viewmodels.MainViewModel
import com.stori.challenge.util.extensions.safeNavigate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainForm(mainViewModel: MainViewModel) {
    val color = MaterialTheme.colorScheme
    val dimensions = LocalDim.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen: Screen? = remember(navBackStackEntry) {
        Screen.allScreens.find { it.route == navBackStackEntry?.destination?.route }
    }
    val authState = mainViewModel.authenticationState.collectAsState().value

    LaunchedEffect(key1 = authState) {
        with(navController) {
            when (authState) {
                is Authenticated -> {
                    val route = when (currentScreen) {
                        is Screen.Register -> Screen.Success.route
                        else -> Screen.Home.route
                    }
                    safeNavigate(
                        route = route,
                        argument = UID to authState.uid,
                        popUpToRoute = currentScreen?.route,
                    )
                }

                is Unauthenticated -> safeNavigate(
                    route = Screen.Login.route,
                    popUpToRoute = currentScreen?.route,
                )

                is Initial -> return@LaunchedEffect
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AnimatedVisibility(visible = currentScreen?.showToolbar ?: false) {
                MediumTopAppBar(
                    title = {
                        Text(
                            modifier = Modifier.padding(horizontal = dimensions.paddingSmall),
                            text = currentScreen?.resTitle?.let { title -> stringResource(id = title) }
                                .orEmpty(),
                            maxLines = 1,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 30.sp,
                            ),
                        )
                    },
                    navigationIcon = {
                        AnimatedVisibility(visible = currentScreen?.showBack == true) {
                            IconButton(onClick = navController::popBackStack) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_back),
                                    contentDescription = "back",
                                    tint = color.inversePrimary,
                                )
                            }
                        }
                    },
                    actions = {
                        currentScreen?.actionIcon?.let { actionIcon ->
                            IconButton(onClick = { mainViewModel.triggerEvent(MainEvent.ActionEvent) }) {
                                Icon(
                                    painter = painterResource(id = actionIcon),
                                    contentDescription = "actionIcon",
                                )
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