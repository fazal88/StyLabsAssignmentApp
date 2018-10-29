package com.androidvoyage.stylabsassignment.models

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class StylabResponse protected constructor(`in`: Parcel) : Parcelable {

    var isSuccess: Boolean = false
    var data: ArrayList<UserInfo>? = null
    var meta: ArrayList<Meta>? = null

    init {
        isSuccess = `in`.readByte().toInt() != 0
        data = `in`.createTypedArrayList(UserInfo.CREATOR)
        meta = `in`.createTypedArrayList(Meta.CREATOR)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeByte((if (isSuccess) 1 else 0).toByte())
        dest.writeTypedList(data)
        dest.writeTypedList(meta)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<StylabResponse> = object : Parcelable.Creator<StylabResponse> {
            override fun createFromParcel(`in`: Parcel): StylabResponse {
                return StylabResponse(`in`)
            }

            override fun newArray(size: Int): Array<StylabResponse?> {
                return arrayOfNulls(size)
            }
        }
    }
}