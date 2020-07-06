package com.chencc.androidstudynotescode.jvm;

import java.lang.ref.WeakReference;

/**
 * �����ò�������
 *-Xms10m  -Xmx10m -XX:+PrintGC
 */
public class TestWeakReference {

    public static void main(String[] args) {
        User user = new User(18, "chen");
        WeakReference<User> userWeak = new WeakReference<User>(user);
        user = null;  // �ɵ�ǿ���ã�ȷ�����ʵ��ֻ��������
        System.out.println(userWeak.get());
        System.gc();            //�ֶ�
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
