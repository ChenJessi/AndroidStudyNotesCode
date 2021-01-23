package com.chen.network.base

import com.chen.network.environment.EnvironmentActivity
import com.chen.network.environment.IEnvironment
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

abstract class NetworkApi : IEnvironment {

    private var mBaseUrl = ""
    private var mOkHttpClient : OkHttpClient? = null

    companion object{
        private lateinit var iNetworkRequiredInfo : INetworkRequiredInfo
        private var mIsFormal = true
        private val retrofitHashMap = mutableMapOf<String, Retrofit>()

        fun init(networkRequiredInfo : INetworkRequiredInfo){
            iNetworkRequiredInfo = networkRequiredInfo
            mIsFormal = EnvironmentActivity.isOfficialEnvironment(networkRequiredInfo.getApplicationContext())
        }
    }


    init {
        if (!mIsFormal){
            mBaseUrl = getTest()
        }else{
            mBaseUrl = getFormal()
        }
    }


    protected open fun getRetrofit(service : Class<Any>) : Retrofit{
        retrofitHashMap[mBaseUrl + service.name]?.let { return it }

        return Retrofit.Builder().apply {
            baseUrl(mBaseUrl)
            client(getOkHttpClient())

        }.build()
    }

    private fun getOkHttpClient() : OkHttpClient{
        return mOkHttpClient ?: OkHttpClient.Builder().apply {
            getInterceptor()?.let { addInterceptor(it) }

            val cacheSize = 100 * 1024 * 1024L
            cache(Cache(iNetworkRequiredInfo.getApplicationContext().cacheDir, cacheSize))
            addInterceptor()
        }.build()
    }




    protected  abstract fun getInterceptor() : Interceptor?
}