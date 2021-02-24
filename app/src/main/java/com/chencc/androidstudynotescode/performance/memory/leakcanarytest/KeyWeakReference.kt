package com.chencc.androidstudynotescode.performance.memory.leakcanarytest

import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

/**
 * 继承自 WeakReference 并且加了一个 key ，通过 key 可以查找到对应的 KeyWeakReference
 */
class KeyWeakReference<T>(val referent : T, val key : String, val name : String, queue: ReferenceQueue<in T>? = null) : WeakReference<T>(referent, queue){

    override fun toString(): String {
        return "KeyWeakReference : key : $key  name : $name"
    }
}