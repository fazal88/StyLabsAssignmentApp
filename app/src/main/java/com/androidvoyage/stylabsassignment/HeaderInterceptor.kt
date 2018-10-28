package com.androidvoyage.stylabsassignment

import android.text.TextUtils
import okhttp3.*
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * Add headers to the all the public api calls.
 * Set error codes in case API call fails.
 */
class HeaderInterceptor : Interceptor {

    val instance: HeaderInterceptor
        get() = this

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var query = chain.request().url().query()
        query = if (TextUtils.isEmpty(query)) "" else query

        val request = chain.request().newBuilder().build()

        try {
            val response = chain.proceed(request)

            if (response.isSuccessful) {
                var err = RequestStatusWrapper.CODE_SUCCESS
                try {
                    val errorCode = response.headers().get("X-API-ErrorCode")
                    err = if (errorCode == null) RequestStatusWrapper.CODE_SUCCESS else Integer.parseInt(errorCode)
                } catch (e: NumberFormatException) {
                    err = RequestStatusWrapper.CODE_INVALID_API_ERROR_CODE
                    LogUtils.logError(e)
                }

                val msg = response.headers().get("X-API-ErrorMessage")
                if (err != RequestStatusWrapper.CODE_SUCCESS) {
                    return response.newBuilder()
                            .code(err)
                            .body(response.body())
                            .message(msg ?: "failure")
                            .build()
                }
            }
            return response
        } catch (e: UnknownHostException) {
            return getNoInternetResponse(request, RequestStatusWrapper.CODE_NO_INTERNET_CONNECTION)
        } catch (e: SocketTimeoutException) {
            return getNoInternetResponse(request, RequestStatusWrapper.CODE_SOCKET_TIMEOUT)
        } catch (e: Exception) {
            return getNoInternetResponse(request, RequestStatusWrapper.CODE_SERVER_ERROR)
        }

    }

    private fun getNoInternetResponse(request: Request, errorCode: Int): Response {
        var msg = ""
        when (errorCode) {
            RequestStatusWrapper.CODE_SOCKET_TIMEOUT -> msg = "Request Timeout. Please try again"

            RequestStatusWrapper.CODE_NO_INTERNET_CONNECTION -> msg = "Please check your internet connection"

            RequestStatusWrapper.CODE_SERVER_ERROR -> msg = "Server error!"
        }
        return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .code(errorCode)
                .message(msg)
                .body(ResponseBody.create(MediaType.parse("application/json"), ""))
                .build()
    }
}