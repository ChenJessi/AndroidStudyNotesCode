package com.chencc.androidstudynotescode.utils.anr

import android.R
import android.os.FileObserver
import android.util.Log


//  /data/anr/
private const val TAG = "ANRFileObserver"
class ANRFileObserver(path : String, mask : Int = 0) : FileObserver(path, mask) {



    override fun onEvent(event: Int, path: String?) {
        when(event){
            FileObserver.ACCESS -> {
                // 文件被访问
                Log.i(TAG, "onEvent:  ACCESS ： $path")
            }
            FileObserver.ATTRIB -> {
                // 文件属性被修改 ， 入 chmod chown  touch等
                Log.i(TAG, "onEvent:  ATTRIB ： $path")
            }
            FileObserver.CLOSE_NOWRITE -> {
                // 不可写文件被 close
                Log.i(TAG, "onEvent:  CLOSE_NOWRITE ： $path")
            }
            FileObserver.CLOSE_WRITE -> {
                // 可写文件被 close
                Log.i(TAG, "onEvent:  CLOSE_WRITE ： $path")
            }
            FileObserver.CREATE -> {
                // 创建新文件
                Log.i(TAG, "onEvent:  CREATE ： $path")
            }
            FileObserver.DELETE -> {
                // 文件被删除
                Log.i(TAG, "onEvent:  DELETE ： $path")
            }
            FileObserver.DELETE_SELF -> {
                // 自删除  如： 一个可执行文件在执行时删除自己
                Log.i(TAG, "onEvent:  DELETE_SELF ： $path")
            }
            FileObserver.MODIFY -> {
                // 文件被修改
                Log.i(TAG, "onEvent:  MODIFY ： $path")
            }
            FileObserver.MOVE_SELF ->{
                // 自移动， 即一个可执行文件在执行时移动自己
                Log.i(TAG, "onEvent:  MOVE_SELF ： $path")
            }
            FileObserver.MOVED_FROM -> {
                // 文件被移走， 如mv
                Log.i(TAG, "onEvent:  MOVED_FROM ： $path")
            }
            FileObserver.MOVED_TO ->{
                // 文件被移来 如 mv cp
                Log.i(TAG, "onEvent:  MOVED_TO ： $path")
            }
            FileObserver.OPEN -> {
                // 文件被 open
                Log.i(TAG, "onEvent:  OPEN ： $path")
            }
            else -> {
                //CLOSE ： 文件被关闭，等同于(IN_CLOSE_WRITE | IN_CLOSE_NOWRITE)
                //ALL_EVENTS ： 包括上面的所有事件
                Log.i(TAG, "DEFAULT($event): $path");
            }

        }

    }
}