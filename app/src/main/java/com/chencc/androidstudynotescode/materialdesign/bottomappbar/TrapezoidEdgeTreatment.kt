package com.chencc.androidstudynotescode.materialdesign.bottomappbar

import android.util.Log
import com.chencc.androidstudynotescode.utils.dp2px
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath

/**
 * @author Created by CHEN on 2020/11/1
 * @email 188669@163.com
 * 梯形缺口
 */
private const val TAG = "TrapezoidEdgeTreatment"
class TrapezoidEdgeTreatment : EdgeTreatment() {

    val radius = 10f.dp2px
    override fun getEdgePath(length: Float, center: Float, interpolation: Float, shapePath: ShapePath) {
        Log.e(TAG, "getEdgePath:   $interpolation")
        shapePath.apply {

            addArc(0f, 0f, 80f, 80f, 180f, 90f);

//            lineTo(center - 40f.dp2px, 0f)
            lineTo(center - 20f.dp2px, 0f)
            addArc(center - 70f.dp2px,0f, center - 20f.dp2px, 50f.dp2px  , 270f, 45f)
            lineTo(center - 15f.dp2px, 30f.dp2px)
            lineTo(center + 15f.dp2px, 30f.dp2px)
            lineTo(center + 27.5f.dp2px, 7.5f.dp2px)
            addArc(center + 20f.dp2px,0f, center + 70f.dp2px, 50f.dp2px  , 225f, 45f)
            lineTo(length, 0f)
        }
    }
}