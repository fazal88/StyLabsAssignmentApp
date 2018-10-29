package com.androidvoyage.stylabsassignment.interactors


import android.os.Handler
import android.support.design.widget.Snackbar
import android.view.View
import com.androidvoyage.stylabsassignment.Presenter.RecycleListPresenter
import com.androidvoyage.stylabsassignment.R
import com.androidvoyage.stylabsassignment.activities.RecycleActivity
import com.androidvoyage.stylabsassignment.models.StylabResponse
import com.androidvoyage.stylabsassignment.models.UserInfo
import com.androidvoyage.stylabsassignment.retrofits.ApiClient
import com.androidvoyage.stylabsassignment.retrofits.RestCallBack
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import retrofit2.Response
import java.util.*

/**
 * Created by Operator on 26.08.2016.
 */
class RecycleInteractor(internal var activity: RecycleActivity) : RecycleListPresenter {

    private val list = ArrayList<UserInfo>()
    private var currentPage = 1

    override fun setFilterData(view: View) {
        if (view.isSelected) {
            val tempList = ArrayList<UserInfo>()
            for (i in list.indices) {
                if (i % 3 != 2) {
                    tempList.add(list[i])
                }
            }
            activity.setFilteredData(tempList)
            Snackbar.make(view, "Every 3rd item removed.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        } else {
            activity.setData(list)
            Snackbar.make(view, "Every 3rd item restored.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun loadMoreData() {
        currentPage += 1
        loadPage(currentPage)
    }

    override fun reloadData() {
        currentPage = 1
        loadPage(currentPage)
    }

    override fun signOut(activity: RecycleActivity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(activity) {
                    // ...
                    activity.signOut()
                }
    }


    private fun loadPage(index: Int) {
        activity.showRefresshing(true)
        activity.listEnded(View.VISIBLE, activity.getString(R.string.str_loading) + " page : $index")
        ApiClient.get()!!.getListItems(index).enqueue(object : RestCallBack<StylabResponse>() {
            override fun onResponseSuccess(response: Response<StylabResponse>) {
                addtoList(response)
            }

            override fun onResponseFailure(errorCode: Int, msg: String?) {
                activity.showRefresshing(false)
                activity.listEnded(View.GONE, activity.getString(R.string.str_error))
            }
        })
    }

    private fun addtoList(response: Response<StylabResponse>) {
        Handler().postDelayed({
            activity.showRefresshing(false)
            if (response.body() != null && response.body()!!.data != null && response.body()!!.data!!.size > 0) {
                if (currentPage == 1 && list.size > 0) {
                    currentPage = 1
                    list.clear()
                    activity.setLastPage(false)
                    activity.setData(response.body()!!.data!!)
                } else {
                    activity.updateList(response.body()!!.data!!)
                }
                list.addAll(response.body()!!.data!!)
                activity.listEnded(View.GONE, "Page : $currentPage")
            } else {
                activity.setLastPage(true)
                activity.listEnded(View.GONE, activity.getString(R.string.str_end))
            }
        }, 2000)
    }


}
