package com.androidvoyage.stylabsassignment

import java.io.File
import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    val CONNECTION_TIMEOUT = 300
    val SOCKET_TIMEOUT = 300
    lateinit var logging: HttpLoggingInterceptor

    var BASE_URL = "http://lightbuzz.in:1353/"

    var debugUrl: String? = null
    var shouldDebug: Boolean = false
    private var REST_CLIENT: API? = null

    var retrofitInstance: Retrofit? = null
        private set

    private var rootUrl: String
        get() = if (shouldDebug) {
            (if (debugUrl != null && !debugUrl!!.isEmpty()) debugUrl else BASE_URL)!!
        } else {
            BASE_URL
        }
        set(url) {
            ApiClient.BASE_URL = (if (url.startsWith("http")) "" else "http://") + url
        }

    init {
        setupRestClient()
    }

    fun get(): API? {
        return REST_CLIENT
    }

    private fun setupRestClient() {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClient.readTimeout(SOCKET_TIMEOUT.toLong(), TimeUnit.SECONDS)

        val httpCacheSize = (10 * 1024 * 1024).toLong() // 10 MiB
        val httpCacheDir = StylabsApplication.appContext!!.getExternalCacheDir()
        // TODO Implement a fallback mechanism if external cache directory is not available
        var cache: okhttp3.Cache? = null
        try {
            if (httpCacheDir != null) {
                cache = okhttp3.Cache(httpCacheDir!!, httpCacheSize)
            }
        } catch (t: Throwable) {
            cache = null
        }

        if (cache != null) {
            httpClient.cache(cache)
        }

        //**//**//**//** Interceptors (Order is important) **//**//**//**//*
        // Request Header interceptor - Adds the request headers and tests for error codes on response
        httpClient.interceptors().add(HeaderInterceptor())

        // For debugging
        // Add debug interceptors only if its a debug build
        if (BuildConfig.DEBUG) {

            //            httpClient.addInterceptor(new ChuckInterceptor(PoonamPandey.getZarinAppContext()));

            // Logging Interceptor (Add this as the last interceptor)
            logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.interceptors().add(logging)
        }

        val builder = Retrofit.Builder()
                .baseUrl(rootUrl)
                .addConverterFactory(GsonConverterFactory.create(MiscUtils.gsonInstance))
                .client(httpClient.build())

        retrofitInstance = builder.build()
        REST_CLIENT = retrofitInstance!!.create(API::class.java)
    }

    fun setDebugMode(shouldDebug: Boolean) {
        ApiClient.shouldDebug = shouldDebug
    }

    object ERROR_CODES {
        val ERROR_CODE_UNKNOWN_HOST = 1
        val ERROR_CODE_JSON_EXCEPTION = 2
        val ERROR_CODE_SOCKET_TIMEOUT = 5
        val ERROR_CODE_UNKNOWN = 99
    }
}
