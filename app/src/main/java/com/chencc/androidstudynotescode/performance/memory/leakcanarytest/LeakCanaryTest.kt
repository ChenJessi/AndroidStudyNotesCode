package com.chencc.androidstudynotescode.performance.memory.leakcanarytest


fun main() {
    val watcher = Watcher()
    var any : Any? = Any()
    println("any : $any")
    any?.let { watcher.watch(it, "") }

    sleep(500)

    any = null

    gc()

    sleep(5000)
    println("查看是否在怀疑列表： ${watcher.getRetainedReferences().size}")
}