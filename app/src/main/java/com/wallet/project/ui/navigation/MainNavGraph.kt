package com.wallet.project.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wallet.project.ui.viewmodels.MainViewModel
import com.wallet.project.ui.views.SplashScreen
import com.wallet.project.ui.views.details.DetailsScreen
import com.wallet.project.ui.views.home.HomeScreen
import com.wallet.project.ui.views.login.SignInScreen
import com.wallet.project.ui.views.register.SignUpScreen
import com.wallet.project.ui.views.register.SuccessfulRegistrationScreen

@Composable
fun MainNavGraph(
    mainViewModel: MainViewModel,
    innerPadding: PaddingValues,
    navController: NavHostController,
    startDestination: String = Screen.Splash.route,
    callBackMessage: (String) -> Unit,
) {
    NavHost(
        modifier = Modifier.padding(innerPadding),
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            route = Screen.Splash.route,
            enterTransition = {
                fadeIn(animationSpec = tween(700))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(700))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(700))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(700))
            },
        ) {
            SplashScreen()
        }
        composable(
            route = Screen.Login.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
        ) {
            SignInScreen(
                navController = navController,
                callBack = callBackMessage,
            )
        }
        composable(
            route = Screen.Register.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
        ) {
            SignUpScreen(
                callBackMessage = callBackMessage,
            )
        }
        composable(
            route = Screen.Home.route,
            arguments = listOf(navArgument(UID) { type = NavType.StringType }),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
        ) {
            HomeScreen(
                mainViewModel = mainViewModel,
                navController = navController,
            )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(TXN) { type = NavType.StringType }),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
        ) {
            DetailsScreen(
                mainViewModel = mainViewModel,
                navController = navController,
            )
        }

        composable(
            route = Screen.Success.route,
            arguments = listOf(navArgument(UID) { type = NavType.StringType }),
            enterTransition = {
                fadeIn(animationSpec = tween(300)) + scaleIn(
                    initialScale = 0.8f,
                    animationSpec = tween(300),
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) + scaleOut(
                    targetScale = 0.8f,
                    animationSpec = tween(300),
                )
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300)) + scaleIn(
                    initialScale = 0.8f,
                    animationSpec = tween(300),
                )
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300)) + scaleOut(
                    targetScale = 0.8f,
                    animationSpec = tween(300),
                )
            },
        ) {
            SuccessfulRegistrationScreen(
                navController = navController,
            )
        }
    }
}
