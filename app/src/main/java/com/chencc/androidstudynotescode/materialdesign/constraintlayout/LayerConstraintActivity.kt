package com.chencc.androidstudynotescode.materialdesign.constraintlayout

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.activity_constraint_layer.*
import kotlin.math.abs
import kotlin.math.sin

private const val TAG = "LayerConstraintActivity"
class LayerConstraintActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layer)

        button8.setOnClickListener {

            val anim = ValueAnimator.ofFloat(0f, 360f)
            anim.duration = 300
            anim.addUpdateListener { animation ->
                val angle = animation.animatedValue as Float
                layer.rotation = angle
                // angle - 180 = 180 - 0 - 180
                //  0 180 0
                // 缩放倍数  1 - 10 - 1
                layer.scaleX = 1 + (180 - abs(angle - 180)) / 20f
                layer.scaleY = 1 + (180 - abs(angle - 180)) / 20f

                // 0 - 1800
                var shift_x = 500 * sin(Math.toRadians((angle * 5).toDouble())).toFloat()
                var shift_y = 500 * sin(Math.toRadians((angle * 7).toDouble())).toFloat()
                layer.translationX = shift_x
                layer.translationY = shift_y

            }
            anim.duration = 4000
            anim.start()
        }
    }
}