package com.wallet.project.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.wallet.project.R

const val UID = "uid"
const val TXN = "txn"

sealed class Screen(
    val route: String,
    @StringRes val resTitle: Int? = null,
    @DrawableRes val actionIcon: Int? = null,
    val showBack: Boolean = false,
    val showToolbar: Boolean = true,
) {
    object Splash : Screen(
        route = "splash",
        showToolbar = false,
    )

    object Login : Screen(
        route = "login",
        showBack = false,
        showToolbar = false,
    )

    object Register : Screen(
        route = "register",
        R.string.register_title,
        showBack = true,
    )

    object Home : Screen(
        route = "home/{$UID}",
        resTitle = R.string.home_title,
        showBack = false,
        actionIcon = R.drawable.ic_logout,
    )

    object Details : Screen(
        route = "details/{$TXN}",
        resTitle = R.string.details_title,
        showBack = true,
    )

    object Success : Screen(
        route = "success/{$UID}",
        showBack = false,
        showToolbar = false,
    )

    companion object {
        val allScreens: List<Screen> = listOf(
            Splash,
            Login,
            Register,
            Success,
            Home,
            Details,
        )

        val Screen.hasActionIcon get() = actionIcon != null
    }
}
