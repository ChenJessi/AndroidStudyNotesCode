package com.chencc.androidstudynotescode.performance.memory


import java.lang.ref.Reference
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference


/**
 * WeakReference和ReferenceQueue联合使用案例
 * 是用来监控某个对象是否被gc回收的手段
 */
fun main() {
    val referenceQueue = ReferenceQueue<Any?>()

    var obj : Any? = Any()

    // 将 obj 对象放入 weakReference 并和一个 referenceQueue 关联
    // 当 obj 被 gc 回收后，盛放它的 weakReference 会被添加到与它关联的 referenceQueue
    val weakReference = WeakReference(obj, referenceQueue)
    println("盛放 obj 的 weakReference  =  $weakReference")

    // obj 置空 干掉强引用
    obj = null

    // 让可以回收的对象回收
    Runtime.getRuntime().gc()

    try {
        Thread.sleep(1000)
    } catch (e: Exception) {
    }

    var findRef : Reference<*>? = null

    do {
        findRef = referenceQueue.poll()
        // 如果能找到上面的 weakReference  说明 obj 被回收了
        println("findRef =  $findRef   findRef 是否等于 weakReference  == ${findRef == weakReference}   ${findRef === weakReference}")
    }while (findRef != null)
}