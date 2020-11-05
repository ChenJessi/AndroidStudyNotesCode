package com.chencc.androidstudynotescode.materialdesign.snackbar

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.chencc.androidstudynotescode.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_snackbar.*

/**
 * Snackbar
 */
class SnackbarActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snackbar)


        snackBar.setOnClickListener {
            Snackbar.make(snackBar, "Hello, SnackBar", Snackbar.LENGTH_LONG).setAction("Action", View.OnClickListener {
                Toast.makeText(this@SnackbarActivity, "SnackBar Action", Toast.LENGTH_SHORT).show()
            }).apply {
                setBackgroundTint(ContextCompat.getColor(this@SnackbarActivity, R.color.color_bg))
            }.show()
        }
    }

}