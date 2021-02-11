package com.chencc.androidstudynotescode

import android.util.SparseArray
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.chencc.androidstudynotescode", appContext.packageName)

        hashmap()
        sparseArray()
    }

    fun hashmap(){
        val t1 = System.currentTimeMillis()
        val map = HashMap<Int, String>()
        for (i in 0..10000){
            map.put(i, i.toString())
        }
        val t2 = System.currentTimeMillis()
        println("hashmap 放入用时 ： t2 - t1 : ${t2 - t1}")

        for (i in  0..10000){
            val a = map.get(i)
        }
        val t3 = System.currentTimeMillis()
        println("hashmap 取出用时 ： t3 - t2 : ${t3-t2}")

    }

    fun sparseArray(){
        val t1 = System.currentTimeMillis()
        val array = SparseArray<String>()
        for (i in 0..10000){
            array.put(i, i.toString())
        }
        val t2 = System.currentTimeMillis()
        println("sparseArray 放入用时 ： t2 - t1 : ${t2 - t1}")

        for (i in 0..10000){
            val a = array.get(i)
        }
        val t3 = System.currentTimeMillis()
        println("sparseArray 取出用时 ： t3 - t2 : ${t3-t2}")

    }
}