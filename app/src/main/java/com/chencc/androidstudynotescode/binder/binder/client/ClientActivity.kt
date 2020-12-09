package com.chencc.androidstudynotescode.binder.binder.client

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.binder.binder.bean.Person
import com.chencc.androidstudynotescode.binder.binder.common.IPersonManager
import com.chencc.androidstudynotescode.binder.binder.common.Stub
import com.chencc.androidstudynotescode.binder.binder.server.RemoteService
import com.chencc.androidstudynotescode.databinding.ActivityClientBinding

/**
 * @author Created by CHEN on 2020/12/8
 * @email 188669@163.com
 */
private const val TAG = "ClientActivity"
class ClientActivity :AppCompatActivity() {

    private var iPersonManager : IPersonManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding =
            DataBindingUtil.setContentView<ActivityClientBinding>(this, R.layout.activity_client)

        mBinding.click = this
        val intent = Intent(this, RemoteService::class.java).apply {
            action = "com.chencc.androidstudynotescode.binder.binder"
        }
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

    fun click1(){
        Log.e(TAG, "click1:   ${Thread.currentThread()}")
        try {
            iPersonManager?.addPerson(Person("ccc", 3))
            val personList = iPersonManager?.getPersonList()
            Log.e(TAG, "click1: personList   ${personList.toString()}" )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private val connection = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e(TAG, "onServiceDisconnected:  success")
            iPersonManager = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e(TAG, "onServiceConnected:   success")
            iPersonManager = Stub.asInterface(service)
        }
    }
}