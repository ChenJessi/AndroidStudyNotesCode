package com.chencc.androidstudynotescode.materialdesign.bottomappbar

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.utils.dp2px
import com.google.android.material.shape.*
import kotlinx.android.synthetic.main.activity_bottom_appbar.*

/**
 * @author Created by CHEN on 2020/10/28
 * @email 188669@163.com
 *
 * BottomAppBar 练习
 */
class BottomAppBarActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_appbar)

        initBar1()
        initBar2()
    }

    private fun initBar1(){
        val triangleEdgeTreatment = TriangleEdgeTreatment(35f.dp2px, true)

        val shapeModel = ShapeAppearanceModel.Builder()
            .setTopEdge(triangleEdgeTreatment)
            .build()

        val drawable = MaterialShapeDrawable(shapeModel)
        drawable.setTint(ContextCompat.getColor(this, R.color.colorPrimary))
        bottomAppBar1.background = drawable

        fab1.shapeAppearanceModel = ShapeAppearanceModel()
        fab1.scaleX = 0.7f
        fab1.scaleY = 0.7f
        fab1.rotation  = 45f
    }


    private fun initBar2(){

        val trapezoidEdgeTreatment = TrapezoidEdgeTreatment()


        val shapeModel = ShapeAppearanceModel.Builder()
            .setTopEdge(trapezoidEdgeTreatment)
            .build()
        val drawable = MaterialShapeDrawable(shapeModel)
        drawable.setTint(ContextCompat.getColor(this, R.color.colorPrimary))

        bottomAppBar2.background = drawable
    }
}