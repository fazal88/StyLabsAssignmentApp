package com.androidvoyage.stylabsassignment.Presenter

import android.view.View
import com.androidvoyage.stylabsassignment.RecycleActivity


interface RecycleListPresenter {
    fun setFilterData(view: View)

    fun loadMoreData()

    fun reloadData()

    fun signOut(activity: RecycleActivity)
}
