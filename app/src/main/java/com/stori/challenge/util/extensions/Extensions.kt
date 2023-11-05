package com.stori.challenge.util.extensions

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Awaits the result of a Task with a coroutine suspension, resuming when the Task is complete.
 *
 * @return The result of the Task if successful, null otherwise.
 * @throws RuntimeException If the Task fails with no specific exception.
 */
suspend fun <T> Task<T>.await(): T? = suspendCoroutine { continuation ->
    addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(task.result)
        } else {
            continuation.resumeWithException(
                task.exception ?: RuntimeException("Unknown task exception"),
            )
        }
    }
}

typealias ArgKeyAndValue = Pair<String, String>?

/**
 * Safely navigates to the specified route with the NavController, using the provided arguments and navigation options.
 * Navigation will fail silently if the route or arguments are invalid.
 *
 * @param route The route to navigate to.
 * @param argument Optional key and value to be replaced in the route string.
 * @param popUpToRoute The route to pop up to before navigation, null by default.
 * @param inclusive Boolean flag to indicate if the popUpToRoute should be inclusive, defaults to true.
 * @param builder Lambda to configure additional options for navigation.
 */
fun NavController.safeNavigate(
    route: String,
    argument: ArgKeyAndValue = null,
    popUpToRoute: String? = null,
    inclusive: Boolean = true,
    builder: NavOptionsBuilder.() -> Unit = {},
) {
    try {
        val options = navOptions {
            popUpToRoute?.let {
                popUpTo(it) { this.inclusive = inclusive }
            }
            builder()
        }

        val finalRoute = argument?.run {
            route.replace("{$first}", second)
        } ?: route

        this.navigate(finalRoute, options)
    } catch (e: IllegalArgumentException) {
        Log.e("NavigationError", "Failed to navigate to $route")
    }
}

/**
 * Wraps an onClick action with a debounce period. Actions triggered in succession within the wait period will be ignored.
 *
 * @param waitMillis The time to wait before registering a new click in milliseconds. Defaults to 700ms.
 * @return A debounced version of the onClick lambda.
 */
@Composable
fun (() -> Unit).debounceClick(waitMillis: Long = 700L): () -> Unit {
    var lastClick by remember { mutableLongStateOf(0L) }
    return {
        if (System.currentTimeMillis() - lastClick >= waitMillis) {
            this.invoke()
            lastClick = System.currentTimeMillis()
        }
    }
}

/**
 * Formats the [Double] to a [String] representation with two decimal places.
 *
 * @return A string representation of the [Double] with two decimal places.
 */
fun Double.toTwoDecimals(): String = String.format("%.2f", this)

fun Activity.setStatusBarColor(color: Color, darkIcons: Boolean) {
    window.statusBarColor = color.toArgb()
    WindowCompat.getInsetsController(
        window,
        window.decorView,
    ).isAppearanceLightStatusBars = darkIcons
}
