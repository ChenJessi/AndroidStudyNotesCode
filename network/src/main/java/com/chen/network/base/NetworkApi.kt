package com.chen.network.base

import com.chen.network.environment.IEnvironment

abstract class NetworkApi : IEnvironment {
    companion object{
        lateinit var iNetworkRequiredInfo : INetworkRequiredInfo

        fun init(networkRequiredInfo : INetworkRequiredInfo){
            iNetworkRequiredInfo = networkRequiredInfo
        }
    }


}