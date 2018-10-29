package com.androidvoyage.stylabsassignment.views

import com.androidvoyage.stylabsassignment.models.UserInfo
import java.util.ArrayList

interface RecycleMainView {

    fun setData(data: ArrayList<UserInfo>)

    fun setFilteredData(filterData: ArrayList<UserInfo>)

    fun updateList(list: ArrayList<UserInfo>)

    fun listEnded(visible: Int, msg: String)

    fun setLastPage(lastPage: Boolean)

    fun showRefresshing(b: Boolean)

    fun signOut()
}