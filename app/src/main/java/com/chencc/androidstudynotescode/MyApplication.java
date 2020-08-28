package com.chencc.androidstudynotescode;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.chencc.androidstudynotescode.androidjvm_class.Hotfix;
import com.chencc.androidstudynotescode.skin.SkinManager;


import java.io.File;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //执行热修复。 插入补丁dex
        //   /sdcard/Android/data/test.dex  dex 本地文件路径
//        Hotfix.installPatch(this,  new File("/sdcard/Android/data/test.dex"));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.INSTANCE.init(this);
    }
}
