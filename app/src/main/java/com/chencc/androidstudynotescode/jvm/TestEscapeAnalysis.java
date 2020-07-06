package com.chencc.androidstudynotescode.jvm;

/**
 *���ݷ�����������
 * ��־ �� -XX:+PrintGC
 * ����  �� -XX:+DoEscapeAnalysis
 * ������ �� -XX:-DoEscapeAnalysis
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


    static void allocate() {//�������ݷ������������ݳ�������
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
