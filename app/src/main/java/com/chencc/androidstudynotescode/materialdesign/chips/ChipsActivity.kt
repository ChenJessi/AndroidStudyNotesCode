package com.chencc.androidstudynotescode.materialdesign.chips

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ImageSpan
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.activity_chips.*

/**
 * chips
 */
private const val TAG = "ChipsActivity"
class ChipsActivity : AppCompatActivity(){

    private var mPreSelectionEnd = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chips)

        chip0.setOnClickListener {
            if (etTest.text.isNotEmpty()){
                val chipsDrawable = ChipDrawable.createFromResource(this@ChipsActivity, R.xml.chip_test)
                chipsDrawable.setBounds(0,0, chipsDrawable.intrinsicWidth, chipsDrawable.intrinsicHeight)
                val span = ImageSpan(chipsDrawable)
                val text = etTest.text
                val newInputText = text.substring(mPreSelectionEnd, text.length)
                chipsDrawable.text = newInputText
                text.setSpan(span, mPreSelectionEnd, text.length, SPAN_EXCLUSIVE_EXCLUSIVE)
                mPreSelectionEnd = text.length
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val max = seekBar?.max ?: 0
                val scale = progress.toDouble() / max
                val drawable = ivShow.background as ClipDrawable
                drawable.level = (scale * 10000).toInt()
                tvInfo.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        button1.setOnClickListener {
            button2.isEnabled = !button2.isEnabled
        }

    }
}