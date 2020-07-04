package com.chencc.androidstudynotescode.jvm;


import java.lang.ref.SoftReference;
import java.util.LinkedList;
import java.util.List;

/**
 * 软引用测试
 *  -Xms10m  -Xmx10m -XX:+PrintGC
 */
public class TestSoftReference {


    public static void main(String[] args) {
        User u = new User(18,"chen");  // new 是强引用
        SoftReference<User> userSoft = new SoftReference<User>(u);  // 软引用、
        u = null;  // 干掉强引用，确保这个实例只有软引用
        System.out.println(userSoft.get()); // 看下对象是否还在

        System.gc();  // 手动 GC
        System.out.println("After gc");
        System.out.println(userSoft.get());
        // 往堆中填充数组，导致oom
        List<byte[]> list = new LinkedList<>();
        try {
            for(int i=0;i<100;i++) {
                //System.out.println("*************"+userSoft.get());
                list.add(new byte[1024*1024*1]); //1M的对象 100m
            }
        } catch (Throwable e) {
            //抛出了OOM异常时打印软引用对象
            System.out.println("Exception*************"+userSoft.get());
        }
    }



    public static class User{
        public int id = 0;
        public String name = "";
        public User(int id, String name) {
            super();
            this.id = id;
            this.name = name;
        }
        @Override
        public String toString() {
            return "User [id=" + id + ", name=" + name + "]";
        }

    }
}
