package com.chencc.androidstudynotescode.customview.fishview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.activity_fish.*

/**
 * 自定义 Drawable 练习
 * 灵动的锦鲤
 */
class FishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fish)

        ivFish.setImageDrawable(FishDrawable())
    }
}