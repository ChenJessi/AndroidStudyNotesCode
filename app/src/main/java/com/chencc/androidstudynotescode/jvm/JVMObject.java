package com.chencc.androidstudynotescode.jvm;



/**
 *�ӵײ������������ʱ������
 * -Xms30m -Xmx30m    -XX:+UseConcMarkSweepGC -XX:-UseCompressedOops
 * -Xss1m
 */

public class JVMObject {
    public final static String MAN_TYPE = "man"; // ����
    public static String WOMAN_TYPE = "woman";  // ��̬����
    public static void main(String[] args) throws InterruptedException {
        Teacher T1 = new Teacher();//����   T1 �Ǿֲ�����
        T1.setName("chencc");
        T1.setSexType(MAN_TYPE);
        T1.setAge(18);
        for (int i=0;i<15;i++){//����15����������
            System.gc();//��������
        }
        Teacher T2 = new Teacher();
        T2.setName("chency");
        T2.setSexType(MAN_TYPE);
        T2.setAge(20);
        Thread.sleep(Integer.MAX_VALUE);//�߳����ߺܾúܾ�
    }

}

class Teacher{
    String name;
    String sexType;
    int age;//��

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSexType() {
        return sexType;
    }
    public void setSexType(String sexType) {
        this.sexType = sexType;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}