package com.chencc.androidstudynotescode.jvm;

/**
 *
 * jvm 对象
 * VM Args：-XX:+PrintGC
 * 判断对象是否存活
 */
public class Isalive {
    public Object instance = null;

    //占据内存，便于分析 GC
    private byte[] bigSize = new byte[10*1024*1024];

    public static void main(String[] args) {
        Isalive objectA = new Isalive(); //objectA 局部变量表 GCRoots
        Isalive objectB = new Isalive(); //objdecB 局部变量表

        //互相引用
        objectA.instance = objectB;
        objectB.instance = objectA;

        //切断可达
        objectA = null;
        objectB = null;

        //强制回收
        System.gc();

    }
}
