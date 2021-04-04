package com.chencc.androidstudynotescode.performance.data_structure

import android.util.SparseArray

/**
 * @author Created by CHEN on 2021/2/11
 * @email 188669@163.com
 * 数据结构测试代码
 */

fun main() {
    hashmap()
    // 需要安卓环境
//    sparseArray()
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

fun sparseArray1(){
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