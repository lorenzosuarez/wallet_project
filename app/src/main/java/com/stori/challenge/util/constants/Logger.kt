package com.stori.challenge.util.constants

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

class Logger {
    fun log(priority: Int, message: String) {
        when (priority) {
            Log.DEBUG -> FirebaseCrashlytics.getInstance().log("DEBUG: $message")
            Log.ERROR -> FirebaseCrashlytics.getInstance().log("ERROR: $message")
        }
    }

    fun logException(e: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(e)
    }
}
