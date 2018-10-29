package com.androidvoyage.stylabsassignment.retrofits

import com.androidvoyage.stylabsassignment.models.StylabResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    /*http://lightbuzz.in:1353/test?pageNo=1*/

    @GET("test?")
    fun getListItems(@Query("pageNo") offset: Int): Call<StylabResponse>

}