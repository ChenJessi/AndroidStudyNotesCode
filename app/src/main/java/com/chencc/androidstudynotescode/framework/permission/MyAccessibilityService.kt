package com.chencc.androidstudynotescode.framework.permission

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
import android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
import android.view.accessibility.AccessibilityNodeInfo

private const val TAG = "MyAccessibilityService"
class MyAccessibilityService : AccessibilityService() {


    private val handleMap = hashMapOf<Int, Boolean>()
    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val nodeEvent = event?.source?.apply {
            val eventType = event.eventType
            if (eventType == TYPE_WINDOW_CONTENT_CHANGED || eventType == TYPE_WINDOW_STATE_CHANGED){
                if (handleMap[event.windowId] == null){
                    val handled = iterateNodesAndHandle(this)
                    if (handled){
                        handleMap[event.windowId] = true
                    }
                }
            }
        }
    }

    /**
     * 遍历节点，模拟点击安装按钮
     */
    private fun iterateNodesAndHandle(nodeInfo: AccessibilityNodeInfo) : Boolean{
        val childCount = nodeInfo.childCount
        when (nodeInfo.className) {
            "android.widget.Button" -> {
                val nodeCotent = nodeInfo.text.toString()
                Log.i(TAG, "iterateNodesAndHandle: nodeCotent : $nodeCotent")
                when(nodeCotent){
                    "安装", "完成" , "确定"->{
                        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        return true
                    }
                }
            }
            "android.widget.ScrollView" ->{
                // 如果有ScrollView的时模拟滑动一下
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
            }
        }
        for (i in 0 until childCount){
            val childNodeInfo = nodeInfo.getChild(i)
            if (iterateNodesAndHandle(childNodeInfo)){
                return true
            }
        }
        return false
    }
}