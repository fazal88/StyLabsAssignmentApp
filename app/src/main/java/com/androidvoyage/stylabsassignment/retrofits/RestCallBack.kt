package com.androidvoyage.stylabsassignment.retrofits

import android.text.TextUtils
import com.androidvoyage.stylabsassignment.utils.LogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Response of the API calls(after successful) is processed here
 *
 * @param <T>
</T> */
abstract class RestCallBack<T> : Callback<T> {

    private var callback: Callback<T>? = null

    private var canceled: Boolean = false

    fun cancel() {
        canceled = true
        callback = null
    }

    abstract fun onResponseSuccess(response: Response<T>)

    abstract fun onResponseFailure(errorCode: Int, msg: String?)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (!call.isCanceled) {
            val errCode: String?
            var statusCode = response.code()
            var errMsg: String? = response.message()

            if (response.isSuccessful) {
                errCode = response.headers().get("X-API-ErrorCode")
                errMsg = response.headers().get("X-API-ErrorMessage")
                statusCode = if (TextUtils.isEmpty(errCode)) 200 else Integer.parseInt(errCode)
                if (statusCode != RequestStatusWrapper.CODE_SUCCESS) {
                    processStatusCode(statusCode, errMsg)
                } else {
                    responseSuccess(response)
                }
            } else {
                processStatusCode(statusCode, errMsg)
            }
        }
    }

    private fun processStatusCode(statusCode: Int, errMsg: String?) {
        responseFailure(statusCode, errMsg)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        LogUtils.logError(t)
        if (!call.isCanceled) {
            responseFailure(ApiClient.ERROR_CODES.ERROR_CODE_JSON_EXCEPTION, t.message)
        }
    }

    private fun responseSuccess(response: Response<T>) {
        try {
            onResponseSuccess(response)
        } catch (t: Throwable) {
            LogUtils.logError(t)
        }

    }

    private fun responseFailure(statusCode: Int, errMsg: String?) {
        try {
            onResponseFailure(statusCode, errMsg)
        } catch (t: Throwable) {
            LogUtils.logError(t)
        }

    }
}
