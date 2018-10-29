package com.androidvoyage.stylabsassignment.models

import android.os.Parcel
import android.os.Parcelable

class Meta protected constructor(`in`: Parcel) : Parcelable {
    var count: String? = null
    var page_no: String? = null

    init {
        count = `in`.readString()
        page_no = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(count)
        dest.writeString(page_no)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<Meta> = object : Parcelable.Creator<Meta> {
            override fun createFromParcel(`in`: Parcel): Meta {
                return Meta(`in`)
            }

            override fun newArray(size: Int): Array<Meta?> {
                return arrayOfNulls(size)
            }
        }
    }
}