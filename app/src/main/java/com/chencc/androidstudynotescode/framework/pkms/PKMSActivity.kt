package com.chencc.androidstudynotescode.framework.pkms

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.databinding.ActivityPkmsTestBinding

/**
 * 静默安装测试用例
 * 实际业务场景中，因为无法拿到系统签名不可用
 */
class PKMSActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPkmsTestBinding>(this, R.layout.activity_pkms_test)


    }


}