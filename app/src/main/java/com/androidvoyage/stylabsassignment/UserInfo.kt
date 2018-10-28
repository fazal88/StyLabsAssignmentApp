package com.androidvoyage.stylabsassignment

import android.os.Parcel
import android.os.Parcelable

class UserInfo protected constructor(`in`: Parcel) : Parcelable {

    var name: String? = null
    var image: String? = null
    var date: String? = null
    var id: String? = null




    init {
        name = `in`.readString()
        image = `in`.readString()
        date = `in`.readString()
        id = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(image)
        dest.writeString(date)
        dest.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<UserInfo> = object : Parcelable.Creator<UserInfo> {
            override fun createFromParcel(`in`: Parcel): UserInfo {
                return UserInfo(`in`)
            }

            override fun newArray(size: Int): Array<UserInfo?> {
                return arrayOfNulls(size)
            }
        }
    }
}

