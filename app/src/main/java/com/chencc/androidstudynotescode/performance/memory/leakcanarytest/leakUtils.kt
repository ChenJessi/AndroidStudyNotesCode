package com.chencc.androidstudynotescode.performance.memory.leakcanarytest





fun sleep(millis : Long){
    println("sleep 时间 ： $millis")
    try {
        Thread.sleep(millis)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}