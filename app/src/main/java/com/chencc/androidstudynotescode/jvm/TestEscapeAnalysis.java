package com.chencc.androidstudynotescode.jvm;

/**
 *逃逸分析测试用例
 * 日志 ： -XX:+PrintGC
 * 启用  ： -XX:+DoEscapeAnalysis
 * 不启用 ： -XX:-DoEscapeAnalysis
 */
public class TestEscapeAnalysis {

    public static void main(String[] args) throws Exception{
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50000000; i++) {
            allocate();
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }


    static void allocate() {//满足逃逸分析（不会逃逸出方法）
        MyObject myObject = new MyObject(2020, 2020.6);
    }

    static class MyObject {
        int a;
        double b;

        MyObject(int a, double b) {
            this.a = a;
            this.b = b;
        }
    }
}
