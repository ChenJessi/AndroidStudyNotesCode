//
// Created by Administrator on 2021/3/10.
//

#include <jni.h>
#include <android/log.h>

extern "C"
JNIEXPORT void JNICALL
Java_com_jessi_crash_CrashReport_testNativeCrash(JNIEnv *env, jobject thiz) {
    int *i = NULL;
    *i = 1;
}



extern "C"
JNIEXPORT void JNICALL
Java_com_jessi_crash_CrashReport_initNativeCrash(JNIEnv *env, jobject thiz, jstring path) {

}