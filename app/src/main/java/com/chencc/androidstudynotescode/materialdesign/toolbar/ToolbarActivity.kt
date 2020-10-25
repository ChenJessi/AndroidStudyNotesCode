package com.chencc.androidstudynotescode.materialdesign.toolbar

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.core.view.children
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.activity_toolbar.*


/**
 * Toolbar  练习
 */
private const val TAG = "ToolbarActivity"
class ToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)

        setSupportActionBar(toolbar1)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar1.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        Log.e(TAG, "onCreateOptionsMenu:  $menuInflater")
        setMenuIconsVisible(menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView?.let {
          it as SearchView
        }

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e(TAG, "onQueryTextSubmit: " )
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e(TAG, "onQueryTextChange: " )
                return true
            }
        })
        searchView?.setOnCloseListener {
            Log.e(TAG, "setOnCloseListener: ")
            false
        }
        searchView?.setOnSearchClickListener {
            Log.e(TAG, "OnSearchClickListener: ")
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_favorite -> {
                Log.e(TAG, "onOptionsItemSelected:  action_favorite" )
                true
            }
            R.id.action_settings -> {
                Log.e(TAG, "onOptionsItemSelected:  action_settings")
                true
            }
            else -> {
                false
            }
        }
    }

    /**
     * 使Popup 菜单图标和文字同时展示
     */
    private fun setMenuIconsVisible(menu : Menu?){
        menu?.let {
            runCatching {
                val method = it::class.java.getDeclaredMethod("setOptionalIconsVisible", Boolean::class.java)
                method.isAccessible = true
                method.invoke(menu, true)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}