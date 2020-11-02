package com.chencc.androidstudynotescode.materialdesign.bottomsheets

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_bottom_sheets.*

/**
 * BottomSheets
 */
private const val TAG = "BottomSheetsActivity"
class BottomSheetsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheets)


        val behavior = BottomSheetBehavior.from(srcBottomSheet)
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.e(TAG, "onSlide:  $slideOffset" )
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Log.e(TAG, "onStateChanged: $newState" )
            }
        })

        btnShow.setOnClickListener {
            if (behavior.state == BottomSheetBehavior.STATE_EXPANDED){
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }else{
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        btnShow1.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this@BottomSheetsActivity).apply {
                val view = layoutInflater.inflate(R.layout.dialog_bottom_sheet, null)
                setContentView(view)
                show()
            }
        }
    }
}