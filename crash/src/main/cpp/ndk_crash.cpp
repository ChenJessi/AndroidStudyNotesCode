//
// Created by Administrator on 2021/3/10.
//

#include <jni.h>
#include <android/log.h>
#include "breakpad/src/client/linux/handler/minidump_descriptor.h"
#include "breakpad/src/client/linux/handler/exception_handler.h"




bool DumpCallback(const google_breakpad::MinidumpDescriptor &descriptor,
                  void *context,
                  bool succeeded) {
    __android_log_print(ANDROID_LOG_ERROR, "ndk_crash", "Dump path: %s", descriptor.path());
    //如果回调返回true，Breakpad将把异常视为已完全处理，禁止任何其他处理程序收到异常通知。
    //如果回调返回false，Breakpad会将异常视为未处理，并允许其他处理程序处理它。
    return false;
}



extern "C"
JNIEXPORT void JNICALL
Java_com_jessi_crash_CrashReport_testNativeCrash(JNIEnv *env, jobject thiz) {
    int *i = NULL;
    *i = 1;
}



extern "C"
JNIEXPORT void JNICALL
Java_com_jessi_crash_CrashReport_initNativeCrash(JNIEnv *env, jobject thiz, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);

    google_breakpad::MinidumpDescriptor descriptor(path);
    static google_breakpad::ExceptionHandler eh(descriptor, NULL, DumpCallback, NULL, true, -1);
    env->ReleaseStringUTFChars(path_, path);
}