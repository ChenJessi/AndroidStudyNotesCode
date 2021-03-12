package com.chencc.androidstudynotescode.binder.binder.bean

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * @author Created by CHEN on 2020/12/8
 * @email 188669@163.com
 */
//@Parcelize
data class Person(var name : String? , var grade : Int) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(grade)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }

}