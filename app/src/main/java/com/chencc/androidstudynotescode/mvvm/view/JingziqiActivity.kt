package com.chencc.androidstudynotescode.mvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.databinding.ActivityJingziqiBinding
import com.chencc.androidstudynotescode.mvvm.viewmodel.JingziqiViewModel

/**
 * mvvm
 */
class JingziqiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBing = DataBindingUtil.setContentView<ActivityJingziqiBinding>(this, R.layout.activity_jingziqi)
        dataBing.viewModel = JingziqiViewModel()
    }
}