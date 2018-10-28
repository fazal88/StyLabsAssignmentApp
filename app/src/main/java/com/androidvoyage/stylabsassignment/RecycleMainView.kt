package com.androidvoyage.stylabsassignment

import java.util.ArrayList

interface RecycleMainView {

    fun setData(data: ArrayList<UserInfo>)

    fun setFilteredData(filterData: ArrayList<UserInfo>)

    fun updateList(list: ArrayList<UserInfo>)

    fun listEnded(inProgress: Boolean, msg: String)

    fun setLastPage(lastPage: Boolean)

    fun showRefresshing(b: Boolean)

    fun signOut()
}