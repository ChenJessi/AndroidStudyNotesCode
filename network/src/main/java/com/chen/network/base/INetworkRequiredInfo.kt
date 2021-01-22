package com.chen.network.base

import android.app.Application

interface  INetworkRequiredInfo {
    fun getAppVersionName() : String
    fun getAppVersionCode() : String
    fun isDebug() : Boolean
    fun getApplicationContext() : Application
}