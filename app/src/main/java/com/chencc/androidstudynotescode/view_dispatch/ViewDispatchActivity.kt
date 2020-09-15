package com.chencc.androidstudynotescode.view_dispatch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.skin.SkinTestActivity
import kotlinx.android.synthetic.main.activity_view_dispatch.*

/**
 * �¼��ַ�
 * ������ͻ����
 */
class ViewDispatchActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_dispatch)
        /**
         * viewpager recyclerView ������ͻ���
         */
        button1.setOnClickListener {
            startActivity(Intent(this@ViewDispatchActivity, RecyclerViewViewPagerActivity::class.java))
        }
        /**
         * viewpager SwipeRefresh ������ͻ���
         */
        button2.setOnClickListener {
            startActivity(Intent(this@ViewDispatchActivity, SwipeRefreshViewPagerActivity::class.java))
        }
    }
}