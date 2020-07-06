package com.chencc.androidstudynotescode.jvm;

import java.lang.ref.WeakReference;

/**
 * 弱引用测试用例
 *-Xms10m  -Xmx10m -XX:+PrintGC
 */
public class TestWeakReference {

    public static void main(String[] args) {
        User user = new User(18, "chen");
        WeakReference<User> userWeak = new WeakReference<User>(user);
        user = null;  // 干掉强引用，确保这个实例只有弱引用
        System.out.println(userWeak.get());
        System.gc();            //手动
        System.out.println("After GC");
        System.out.println(userWeak.get());
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
