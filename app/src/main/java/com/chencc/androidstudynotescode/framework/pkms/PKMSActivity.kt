package com.chencc.androidstudynotescode.framework.pkms

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.databinding.ActivityPkmsTestBinding

class PKMSActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPkmsTestBinding>(this, R.layout.activity_pkms_test)

    }
}