package com.androidvoyage.stylabsassignment.interactors


import android.os.Handler
import android.view.View
import com.androidvoyage.stylabsassignment.*
import com.androidvoyage.stylabsassignment.Presenter.RecycleListPresenter
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

    override fun setFilterData(fab: View) {
        if (fab.isSelected) {
            val tempList = ArrayList<UserInfo>()
            for (i in list.indices) {
                if (i % 3 != 2) {
                    tempList.add(list[i])
                }
            }
            activity.setFilteredData(tempList)
        } else {
            activity.setData(list)
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
        Handler().postDelayed({
            ApiClient.get()!!.getListItems(index).enqueue(object : RestCallBack<StylabResponse>() {
                override fun onResponseSuccess(response: Response<StylabResponse>) {
                    activity.showRefresshing(false)
                    if (response != null && response.body() != null && response.body()!!.data != null && response.body()!!.data!!.size > 0) {
                        if (currentPage == 1 && list != null && list.size > 0) {
                            currentPage = 1
                            list.clear()
                            activity.setLastPage(false)
                            activity.listEnded(true, activity.getString(R.string.str_loading))
                            activity.setData(response.body()!!.data!!)
                        } else {
                            activity.updateList(response.body()!!.data!!)
                        }
                        list.addAll(response.body()!!.data!!)
                    } else {
                        activity.setLastPage(true)
                        activity.listEnded(false, activity.getString(R.string.str_end))
                    }
                }

                override fun onResponseFailure(errorCode: Int, msg: String?) {
                    activity.showRefresshing(false)
                    activity.listEnded(false, activity.getString(R.string.str_error))
                }
            })
        }, 2000)
    }


}
