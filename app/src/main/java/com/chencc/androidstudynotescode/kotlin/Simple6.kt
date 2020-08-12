package com.chencc.androidstudynotescode.kotlin

import android.view.View
import androidx.core.widget.NestedScrollView
import kotlinx.coroutines.*

/**
 * Ð­³Ì
 */
fun main() {

    GlobalScope.launch {

        withContext(Dispatchers.IO){
            println("===========8 : ${Thread.currentThread().name}")
            delay(2000)
            println("===========9 : ${Thread.currentThread().name}")
        }
        withContext(Dispatchers.Default){
            println("===========6 : ${Thread.currentThread().name}")
            delay(3000)
            println("===========1 : ${Thread.currentThread().name}")
        }
        println("===========5 : ${Thread.currentThread().name}")
        test()
        println("===========4 : ${Thread.currentThread().name}")
        withContext(Dispatchers.Default){
            println("===========7 : ${Thread.currentThread().name}")
        }
    }
    Thread.sleep(20000)
}

suspend fun test(){
    withContext(Dispatchers.IO){
        println("===========2 : ${Thread.currentThread().name}")
        delay(2000)
        println("===========3 : ${Thread.currentThread().name}")
    }
}