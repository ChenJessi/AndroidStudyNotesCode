package com.chencc.androidstudynotescode.materialdesign.materialtext

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_materialtext.*
import kotlinx.android.synthetic.main.item_test.*

/**
 *
 * TextInputLayout && TextInputLayout
 */
private const val TAG = "MaterialTextActivity"
class MaterialTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materialtext)

        button1.setOnClickListener {
            if (textInputLayout.error == null || textInputLayout.error == ""){
                showError(textInputLayout, "错误展示！！！")
            }else{
                textInputLayout.error = ""
            }
        }

    }

    private fun showError(textInputLayout : TextInputLayout, errorStr : String){
        textInputLayout.apply {
            error = errorStr
            editText.apply {
                isFocusable = true
                isFocusableInTouchMode = true
                requestFocus()
            }
        }

    }
}