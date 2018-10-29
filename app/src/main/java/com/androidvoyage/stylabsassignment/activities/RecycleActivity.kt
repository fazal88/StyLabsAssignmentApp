package com.androidvoyage.stylabsassignment.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

import com.androidvoyage.stylabsassignment.Presenter.RecycleListPresenter
import com.androidvoyage.stylabsassignment.R
import com.androidvoyage.stylabsassignment.views.RecycleMainView
import com.androidvoyage.stylabsassignment.models.UserInfo
import com.androidvoyage.stylabsassignment.adapters.RCVAdapter
import com.androidvoyage.stylabsassignment.interactors.RecycleInteractor
import com.androidvoyage.stylabsassignment.listeners.PaginationScrollListener

import java.util.ArrayList

class RecycleActivity : TransitionActivity(), View.OnClickListener, RecycleMainView {

    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var context: Context? = null
    private var rcv_list: RecyclerView? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var adapter: RCVAdapter? = null
    private var tvLoading: TextView? = null
    private var pbLoading: ProgressBar? = null
    private var fab: FloatingActionButton? = null

    private var isRefreshing = false
    private var isPageEnd = false

    private var presenter: RecycleListPresenter? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById<View>(R.id.toolbar_main) as Toolbar)
        context = this
        if (intent != null) {
            val firstname = intent.getStringExtra(this.getString(R.string.first_name))
            val lastname = intent.getStringExtra(this.getString(R.string.last_name))
            title = "Hello, $firstname $lastname"
        }
        initViews()
        setListener()
        presenter = RecycleInteractor(this)
        presenter!!.reloadData()
    }

    private fun initViews() {
        fab = findViewById(R.id.fab)
        mSwipeRefreshLayout = findViewById<View>(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        pbLoading = findViewById(R.id.pb_loading)
        tvLoading = findViewById(R.id.tv_loading)
        rcv_list = findViewById<View>(R.id.rcv_list) as RecyclerView

        rcv_list!!.setHasFixedSize(true)
        rcv_list!!.isNestedScrollingEnabled = false
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcv_list!!.layoutManager = linearLayoutManager
        adapter = RCVAdapter(context!!)
        rcv_list!!.adapter = adapter
    }

    private fun setListener() {

        fab!!.setOnClickListener(this)

        mSwipeRefreshLayout!!.setOnRefreshListener {
            // Refresh items
            presenter!!.reloadData()
        }

        rcv_list!!.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager!!) {

            override val isLastPage : Boolean
            get() = isPageEnd

            override val isLoading: Boolean
            get() = isRefreshing

            override fun loadMoreItems() {
                presenter!!.loadMoreData()
            }
        })
    }

    override fun setData(data: ArrayList<UserInfo>) {
        adapter!!.filteredList(data)
        if (fab!!.isSelected) {
            fab!!.isSelected = false
        }
    }

    override fun setFilteredData(filterData: ArrayList<UserInfo>) {
        adapter!!.filteredList(filterData)
    }

    override fun updateList(list: ArrayList<UserInfo>) {
        adapter!!.addAll(list)
    }

    override fun listEnded(visible: Int, msg: String) {
        pbLoading!!.visibility = visible
        tvLoading!!.text = msg
    }

    override fun showRefresshing(b: Boolean) {
        isRefreshing = b
        mSwipeRefreshLayout!!.isRefreshing = b
    }

    override fun setLastPage(lastPage: Boolean) {
        isPageEnd = lastPage
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_signout -> {
                presenter!!.signOut(this@RecycleActivity)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun signOut() {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    override fun onClick(v: View) {
        fab!!.isSelected = !fab!!.isSelected
        presenter!!.setFilterData(fab!!)

    }
}
