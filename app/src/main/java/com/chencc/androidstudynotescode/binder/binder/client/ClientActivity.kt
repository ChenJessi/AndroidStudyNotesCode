package com.chencc.androidstudynotescode.binder.binder.client

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.databinding.ActivityClientBinding

/**
 * @author Created by CHEN on 2020/12/8
 * @email 188669@163.com
 */
class ClientActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityClientBinding>(this, R.layout.activity_client)
    }
}