package com.chen.network.commoninterceptor

import com.chen.network.base.INetworkRequiredInfo
import okhttp3.Interceptor
import okhttp3.Response

class CommonRequestInterceptor(var requiredInfo : INetworkRequiredInfo) : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {

        return chain
    }
}