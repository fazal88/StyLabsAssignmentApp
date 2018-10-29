package com.androidvoyage.stylabsassignment.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.*

/**
 * To get the gson instance.
 * Used in Retrofit.Builders convertor factory to convert response in this format.
 */
object MiscUtils {

    internal var gson: Gson? = null

    val gsonInstance: Gson
        get() {
            if (gson == null) {
                gson = Gson()
            }
            return gson!!
        }

    init {
        gson = GsonBuilder()
                .registerTypeAdapter(Date::class.java, UTCDateTypeAdapter("yyyy-MM-dd HH:mm:ss"))
                .create()
    }
}