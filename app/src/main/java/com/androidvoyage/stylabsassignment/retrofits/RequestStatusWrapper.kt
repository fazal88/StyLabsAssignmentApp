package com.androidvoyage.stylabsassignment.retrofits

import android.support.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class RequestStatusWrapper {
    @Code
    @get:Code
    var statusCode = CODE_NULL

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(CODE_INVALID_PARTNER_ID, CODE_INVALID_TIMESTAMP, CODE_INVALID_AUTHORIZATION_HEADER, CODE_NULL, CODE_LOADING, CODE_SERVER_ERROR, CODE_SUCCESS, CODE_NO_DATA, CODE_NO_INTERNET_CONNECTION, CODE_SOCKET_TIMEOUT, CODE_OFFER_EXPIRED)
    annotation class Code

    companion object {

        const val CODE_NULL = -1
        const val CODE_LOADING = 0
        const val CODE_SUCCESS = 200
        val CODE_CREATED = 201

        /**
         * Internet Connection Issues
         */
        const val CODE_NO_INTERNET_CONNECTION = 65535
        const val CODE_SOCKET_TIMEOUT = 65534

        /**
         * Empty Data Issues
         */
        const val CODE_NO_DATA = 204

        /**
         * Server/API Issue
         */
        const val CODE_SERVER_ERROR = 503

        val CODE_INVALID_API_ERROR_CODE = 63000

        const val CODE_OFFER_EXPIRED = 555

        const val CODE_INVALID_PARTNER_ID = 4001
        const val CODE_INVALID_TIMESTAMP = 4002
        const val CODE_INVALID_AUTHORIZATION_HEADER = 4003
    }
}
