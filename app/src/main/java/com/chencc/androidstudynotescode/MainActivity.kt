package com.chencc.androidstudynotescode

import android.app.Activity
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.core.content.FileProvider
import com.chencc.androidstudynotescode.androidapi.ActivityResultTestActivity
import com.chencc.androidstudynotescode.binder.binder.client.ClientActivity
import com.chencc.androidstudynotescode.binder.mmap.MmapTestActivity
import com.chencc.androidstudynotescode.customview.CustomViewActivity
import com.chencc.androidstudynotescode.draw_text.DrawTextActivity
import com.chencc.androidstudynotescode.hook.activityhook.MainHookActivity
import com.chencc.androidstudynotescode.lazyfragment.LazyFragmentActivity
import com.chencc.androidstudynotescode.materialdesign.MaterialDesignActivity
import com.chencc.androidstudynotescode.nestedscroll.NestedScrollActivity
import com.chencc.androidstudynotescode.skin.SkinTestActivity
import com.chencc.androidstudynotescode.utils.*
import com.chencc.androidstudynotescode.utils.battery.BatteryActivity
import com.chencc.androidstudynotescode.view_dispatch.ViewDispatchActivity
import com.jessi.arouter_annotation_java.ARouter
import com.jessi.arouter_api_java.ARouterGroup
import com.jessi.arouter_api_java.RouterManager

import com.jessi.crash.CrashReport
import dalvik.system.PathClassLoader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream


private const val TAG = "MainActivity"

@ARouter(path = "/app/MainActivity", group = "app")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        AsyncLayoutInflater(this).inflate(
            R.layout.activity_main,
            null
        ) { view, resid, parent ->
            Log.e("MainActivity", "setContentView")
            setContentView(view)
            initListener()
        }


//        Log.e("MainActivity", "Activity.class 由： + ${Activity::class.java.classLoader} + 加载")
//        Log.e("MainActivity", "MainActivity.class 由： + $classLoader + 加载")

//        Test.test()
//        test1()


    }



    private fun initListener(){
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

        /**
         * MaterialDesign
         */
        text8.setOnClickListener {
            startActivity(Intent(this@MainActivity, MaterialDesignActivity::class.java))
        }
        /**
         * binder mmap
         */
        button1.setOnClickListener {
            startActivity(Intent(this@MainActivity, MmapTestActivity::class.java))
        }
        /**
         * binder
         */
        button2.setOnClickListener {
            startActivity(Intent(this@MainActivity, ClientActivity::class.java))
        }
        /**
         * BSPatch测试
         */
        button3.setOnClickListener {
            patch()
        }
        /**
         * hook
         */
        button4.setOnClickListener {
            startActivity(Intent(this@MainActivity, MainHookActivity::class.java))
        }
        /**
         *  ActivityResult
         */
        button5.setOnClickListener {
//            startActivity(Intent(this@MainActivity, ActivityResultTestActivity::class.java))
            RouterManager.getInstance().build("/result/ActivityResultTestActivity")
                .withInt("intKey", 1)
                .withString("stringKey", "test")
                .navigation(this@MainActivity)
        }

        /**
         * Battary 电量优化
         */
        button7.setOnClickListener {
            startActivity(Intent(this@MainActivity, BatteryActivity::class.java))
            RouterManager.getInstance().build("/optimization/BatteryActivity")
                .navigation(this@MainActivity)

//            CrashReport.testNativeCrash()
        }
//        Log.e(TAG, "onCreate: ${getExternalFilesDir("")?.absolutePath}")

    }





    // 内存不足
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        // 主动释放内存
    }

    override fun onLowMemory() {
        super.onLowMemory()
        // 低内存
        // 尽可能释放内存
    }




  // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  增量更新测试  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * 增量更新测试
     */

    private fun patch(){
        val newFile = File(getExternalFilesDir("apk"), "app.apk")
//        val patchFile = File(getExternalFilesDir("apk"), "patch.apk")
        val patchFile = File("/sdcard/Android/data/patch.apk")
        Log.e(
            TAG,
            "patch: sourceDir : ${applicationInfo.sourceDir}   ${newFile.absolutePath}   ${patchFile.absolutePath}"
        )
        val result = JNIUtils.patch(
            applicationInfo.sourceDir,
            newFile.absolutePath,
            patchFile.absolutePath
        )
        if (result == 0){
            install(newFile)
        }
    }


    private fun install(file: File){
        val intent = Intent(ACTION_VIEW).apply {
            addFlags(FLAG_ACTIVITY_NEW_TASK)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// 7.0+
                val apkUri = FileProvider.getUriForFile(
                    this@MainActivity,
                    "$packageName.fileprovider",
                    file
                )
                addFlags(FLAG_GRANT_READ_URI_PERMISSION)
                setDataAndType(apkUri, "application/vnd.android.package-archive");
            }else{
                setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
        }
        startActivity(intent)
    }


    private fun testDiff(){
        val file = copy4Assets()
        val pathClassLoader = PathClassLoader(file.absolutePath, classLoader)
        try {
            val test = pathClassLoader.loadClass("Test")
            val main = test.getDeclaredMethod("test")
            main.invoke(null)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun copy4Assets() : File{
        val externalFilesDir = getExternalFilesDir("")
        val dexFile = File(externalFilesDir, "new.dex")
        if (!dexFile.exists()){
            var bufferedInputStream : BufferedInputStream? = null
            var bufferedOutputStream : BufferedOutputStream? = null

            try {
                bufferedInputStream = BufferedInputStream(assets.open("new.dex"))
                bufferedOutputStream = BufferedOutputStream(FileOutputStream(dexFile))
                val buffer = ByteArray(4096)
                var len = 0
                while ( bufferedInputStream.read(buffer).also { len = it } != -1){
                    bufferedOutputStream.write(buffer, 0, len)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    bufferedInputStream?.close()
                    bufferedOutputStream?.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return dexFile
    }

// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  增量更新测试结束  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>






//
//    fun text0() {
//        GlobalScope.launch {
//            flow<Int> {
//                (60 downTo 0).forEach {
//                    delay(1000)
//                    emit(it)
//                }
//            }
//                .flowOn(Dispatchers.Main)
//                .collect {
//                    Log.e("TAG", "text0:   $it")
//                }
//        }
//    }
//    fun launchUI(block : suspend ()->Unit){
//        GlobalScope.launch {
//            block.invoke()
//        }
//    }
//
//    fun test2(){
//        launchUI {
//            flow<Int> {
//                (60 downTo 0).forEach {
//                    delay(1000)
//                    emit(it)
//                }
//            }
//                .flowOn(Dispatchers.Main)
//                .collect {
//                    Log.e("TAG", "text0:   $it")
//                }
//        }
//    }
//
//
//    /**
//     * 测试协程
//     */
//    fun test1() {
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
//    }


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