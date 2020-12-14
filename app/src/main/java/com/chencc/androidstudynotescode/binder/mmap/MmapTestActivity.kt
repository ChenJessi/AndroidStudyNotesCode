package com.chencc.androidstudynotescode.binder.mmap

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.databinding.ActivityMmapTestBinding
import com.chencc.androidstudynotescode.utils.JNIUtils

/**
 * @author Created by CHEN on 2020/12/7
 * @email 188669@163.com
 * mmap 读写文件测试
 */
class MmapTestActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMmapTestBinding>(this, R.layout.activity_mmap_test)
        binding.button1.setOnClickListener {
            JNIUtils.writeTest()
        }
        binding.button2.setOnClickListener {
            JNIUtils.readTest()
        }
    }


}