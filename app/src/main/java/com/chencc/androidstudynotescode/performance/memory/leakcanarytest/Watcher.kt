package com.chencc.androidstudynotescode.performance.memory.leakcanarytest

import kotlinx.coroutines.delay
import java.lang.ref.ReferenceQueue
import java.util.*
import java.util.concurrent.Executors

class Watcher {
    // 观察列表
    private val watchedReferences = hashMapOf<String, KeyWeakReference<Any>>()
    // 怀疑列表
    private val retainedReferences = hashMapOf<String, KeyWeakReference<Any>>()

    /**
     * 引用队列，相当于一个监视器， 所有需要监视的对象，存放监视对象的容器，都与之关联
     * 当被监视的对象被 gc 后，对应的容器就会被加入到queue
     */
    private val queue = ReferenceQueue<Any>()


    fun watch(watchedReference : Any, referenceName : String){
        println("开始 watch 对象......")

        // 1. 在没有被监视之前，先清理观察列表和怀疑列表
        removeWeaklyReachableReferences()

        // 2. 为要监视的对象生成一个唯一的 uuid
        // 相当于 把要监视的对象和容器 与 应用队列 建立联系
        val key = UUID.randomUUID().toString()
        println("待监视的对象的 key ： $key")

        // 3. 让 watchedReference 与一个 KeyWeakReference 建立一对一映射关系，并与引用队列 queue 关联
        val reference = KeyWeakReference(watchedReference, key, "", queue)

        // 4. 加入到观察列表
        watchedReferences[key] = reference

        // 5. 过 5 秒之后看是否还在观察列表，如果还在，就加入到怀疑列表
        val executor= Executors.newSingleThreadExecutor()
        executor.execute {

        }

    }






    /**
     * 清理观察列表和怀疑列表的引用容器
     */
    private fun removeWeaklyReachableReferences() {
        println("清理列表...")
        var findRef : KeyWeakReference<*>? = null

        do {
            findRef = queue.poll() as KeyWeakReference<*>?
            println("findRef : $findRef")
            // 不为空说明 对应的对象被 gc 回收了，那么可以把对应的容器从观察列表，怀疑列表移除
            if (findRef != null){
                println("被回收对象的 key 值 ： ${findRef.key}")
                // 根据 key 把观察列表中对应的容器移除
                val removeRef = watchedReferences.remove(findRef.key)
                // 如果 removeRef == null 那么有可能已经被放入怀疑列表了
                // 那么从怀疑列表移除
                if (removeRef != null){
                    retainedReferences.remove(findRef.key)
                }
            }
        }while (findRef != null)  // 获取所有放到 referenceQueue 队列的引用容器
    }


}