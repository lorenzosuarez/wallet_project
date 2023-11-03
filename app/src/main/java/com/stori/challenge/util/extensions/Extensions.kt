package com.stori.challenge.util.extensions

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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
fun Double.toTwoDecimals(): String = String.format("%.2f", this)



