package com.androidvoyage.stylabsassignment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.ArrayList

class RCVAdapter(private val context: Context) : RecyclerView.Adapter<RCVAdapter.ViewHolder>() {
    private var adapterList: ArrayList<UserInfo>? = ArrayList<UserInfo>()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv, parent, false)
        return ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(adapterList!![position].image).placeholder(R.color.colorPrimaryDark).into(holder.ivImage)
        holder.tvId.setText(adapterList!![position].id)
        holder.tvIndex.text = "" + (position + 1)
        holder.tvName.setText(adapterList!![position].name)
        holder.tvDate.setText(adapterList!![position].date)
    }


    override fun getItemCount(): Int {
        return if (adapterList != null && adapterList!!.size > 0) {
            adapterList!!.size
        } else {
            0
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun filteredList(tempList: ArrayList<UserInfo>) {
        adapterList = tempList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val tvId: TextView
        internal val tvIndex: TextView
        internal val ivImage: ImageView
        internal val tvName: TextView
        internal val tvDate: TextView

        init {
            tvId = view.findViewById<View>(R.id.tv_id) as TextView
            tvIndex = view.findViewById<View>(R.id.tv_index) as TextView
            ivImage = view.findViewById<View>(R.id.iv_image) as ImageView
            tvName = view.findViewById<View>(R.id.tv_name) as TextView
            tvDate = view.findViewById<View>(R.id.tv_date) as TextView
        }
    }

    fun add(r: UserInfo) {
        adapterList!!.add(r)
        notifyItemInserted(adapterList!!.size - 1)
    }

    fun addAll(newList: ArrayList<UserInfo>) {
        adapterList!!.addAll(newList)
        notifyItemRangeInserted(itemCount - newList.size, newList.size)
    }

    fun remove(r: UserInfo) {
        val position = adapterList!!.indexOf(r)
        if (position > -1) {
            adapterList!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        adapterList!!.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): UserInfo {
        return adapterList!![position]
    }
}