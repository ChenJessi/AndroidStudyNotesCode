package com.chencc.androidstudynotescode

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.chencc.androidstudynotescode.androidjvm_class_test.Test
import com.chencc.androidstudynotescode.androidjvm_class_test.Test.test
import com.chencc.androidstudynotescode.customview.CustomViewActivity
import com.chencc.androidstudynotescode.customview.flowLayout.TestFlowActivity
import com.chencc.androidstudynotescode.customview.viewpager.TestViewPagerActivity
import com.chencc.androidstudynotescode.draw_text.DrawTextActivity
import com.chencc.androidstudynotescode.lazyfragment.LazyFragmentActivity
import com.chencc.androidstudynotescode.nestedscroll.NestedScrollActivity
import com.chencc.androidstudynotescode.skin.SkinTestActivity
import com.chencc.androidstudynotescode.utils.getResId
import com.chencc.androidstudynotescode.view_dispatch.ViewDispatchActivity
import com.qmuiteam.qmui.util.QMUIDrawableHelper
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.zip.ZipFile

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("MainActivity", "Activity.class 由： + ${Activity::class.java.classLoader} + 加载")
        Log.e("MainActivity", "MainActivity.class 由： + $classLoader + 加载")

//        Test.test()
//        test1()
        text1.setOnClickListener {
            startActivity(Intent(this@MainActivity, SkinTestActivity::class.java))
        }
        text2.setOnClickListener {
            startActivity(Intent(this@MainActivity, CustomViewActivity::class.java))
        }
        text4.setOnClickListener {
            startActivity(Intent(this@MainActivity, NestedScrollActivity::class.java))
        }
        text5.setOnClickListener {
            startActivity(Intent(this@MainActivity, ViewDispatchActivity::class.java))
        }
        /**
         * 文字绘制
         */
        button6.setOnClickListener {
            startActivity(Intent(this@MainActivity, DrawTextActivity::class.java))
        }
        /**
         * fragment 懒加载
         */
        text7.setOnClickListener {
            startActivity(Intent(this@MainActivity, LazyFragmentActivity::class.java))
        }
    }


    fun text0() {
        GlobalScope.launch {
            flow<Int> {
                (60 downTo 0).forEach {
                    delay(1000)
                    emit(it)
                }
            }
                .flowOn(Dispatchers.Main)
                .collect {
                    Log.e("TAG", "text0:   $it")
                }
        }
    }
    fun launchUI(block : suspend ()->Unit){
        GlobalScope.launch {
            block.invoke()
        }
    }

    fun test2(){
        launchUI {
            flow<Int> {
                (60 downTo 0).forEach {
                    delay(1000)
                    emit(it)
                }
            }
                .flowOn(Dispatchers.Main)
                .collect {
                    Log.e("TAG", "text0:   $it")
                }
        }
    }


    /**
     * 测试协程
     */
    fun test1() {
//        GlobalScope.launch {
//
//            launch {
//                testxc1()
//                testxc()
//            }
//
//            withContext(Dispatchers.IO) {
//                MLog("t===========1 : ${Thread.currentThread().name}")
//
//                delay(2000)
//                MLog("t===========3 : ${Thread.currentThread().name}")
//            }
//            withContext(Dispatchers.IO){
//                println("===========8 : ${Thread.currentThread().name}")
//                delay(2000)
//                println("===========9 : ${Thread.currentThread().name}")
//                withContext(Dispatchers.IO){
//                    delay(5000)
//                    println("===========10 : ${Thread.currentThread().name}")
//                }
//            }
//            withContext(Dispatchers.Main){
//                println("===========6 : ${Thread.currentThread().name}")
//                delay(3000)
//                println("===========1 : ${Thread.currentThread().name}")
//            }
//            println("===========5 : ${Thread.currentThread().name}")
//            com.chencc.androidstudynotescode.kotlin.test()
//            println("===========4 : ${Thread.currentThread().name}")
//            withContext(Dispatchers.Main){
//                println("===========7 : ${Thread.currentThread().name}")
//            }
//        }


//        MLog("test11111===========2 : ${Thread.currentThread().name}")
    }


//    fun testView() {
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = testAdapter()
//    }
}


suspend fun testxc() = withContext(Dispatchers.IO) {
    MLog("test===========2 : ${Thread.currentThread().name}")
    delay(1000)
    MLog("test===========3 : ${Thread.currentThread().name}")
}

suspend fun testxc1() = withContext(Dispatchers.IO) {
    MLog("test1===========1 : ${Thread.currentThread().name}")
    delay(2000)
    MLog("test1===========3 : ${Thread.currentThread().name}")
}
//class testAdapter : RecyclerView.Adapter<testAdapter.ViewHolder>(){
//
//
//
//
//    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view){
//        public var text = view.findViewById<TextView>(R.id.text)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_test, parent, false));
//    }
//
//    override fun getItemCount(): Int {
//        return 10
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.text.text = "这是第$position 条数据"
//    }
//}

fun MLog(string: String) {
    Log.e("TAG", string)
}