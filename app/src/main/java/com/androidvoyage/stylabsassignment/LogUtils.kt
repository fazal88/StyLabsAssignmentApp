package com.androidvoyage.stylabsassignment

import android.util.Log

object LogUtils {
    private val LOG_PREFIX = "CD_"

    fun makeLogTag(str: String): String {
        return LOG_PREFIX + str
    }

    /**
     * Don't use this when obfuscating class names!
     */
    fun makeLogTag(cls: Class<*>): String {
        return makeLogTag(cls.simpleName)
    }

    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    fun d(tag: String, message: String, cause: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message, cause)
        }
    }

    fun v(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message)
        }
    }

    fun v(tag: String, message: String, cause: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message, cause)
        }
    }

    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }

    fun i(tag: String, message: String, cause: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message, cause)
        }
    }

    fun w(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message)
        }
    }

    fun w(tag: String, message: String, cause: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message, cause)
        }
    }

    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }

    @Synchronized
    fun e(tag: String, message: String, cause: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message, cause)
        }
    }

    fun logError(t: Throwable?) {
        if (t != null) {
            if (BuildConfig.DEBUG) {
                t.printStackTrace()
            }
        }
    }
}