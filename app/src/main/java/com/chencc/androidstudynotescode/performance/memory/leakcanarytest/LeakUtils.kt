package com.chencc.androidstudynotescode.performance.memory.leakcanarytest





fun sleep(millis : Long){
    println("sleep 时间 ： $millis")
    try {
        Thread.sleep(millis)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun gc(){
    println("执行gc...")
    //主要这里不是使用System.gc,因为它仅仅是通知系统在合适的时间进行一次垃圾回收操作
    //实际上并不保证一定执行
    Runtime.getRuntime().gc()
    sleep(100)
    System.runFinalization()
}