package com.chencc.androidstudynotescode.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * ĞéÒıÓÃ PhantomReference ²âÊÔÓÃÀı
 *
 */
public class TestPhantomReference {

    public static void main(String[] args) {
        ReferenceQueue<String> queue = new ReferenceQueue<String>();
        PhantomReference<String> pr = new PhantomReference<String>(new String("hello"), queue);
        System.out.println(pr.get());
    }
}
