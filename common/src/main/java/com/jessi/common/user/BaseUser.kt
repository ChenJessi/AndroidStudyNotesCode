package com.jessi.common.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Created by CHEN on 2021/5/31
 * @email 188669@163.com
 */
@Parcelize
data class BaseUser(
    val name: String? = null,
    val account: String? = null,
    val age :Int = 0
) : Parcelable