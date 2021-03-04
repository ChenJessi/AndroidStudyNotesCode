package com.chencc.androidstudynotescode;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.chencc.androidstudynotescode.skin.SkinManager;
import com.chencc.androidstudynotescode.utils.anr.ANRWatchDog;


import java.io.File;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";



    public MyApplication(){
        // 需要手动开启存储权限
//        Debug.startMethodTracingSampling(new File(Environment.getExternalStorageDirectory(),
//                "jessi").getAbsolutePath(), 8 * 1024 * 1024, 1_000);
//        Debug.startMethodTracing(new File(Environment.getExternalStorageDirectory(),
//                "jessi").getAbsolutePath());
    }


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
//        SkinManager.INSTANCE.init(this);

        if (BuildConfig.DEBUG){
            // //线程检测策略
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()   //读、写操作
                    .detectDiskWrites()
                    .detectNetwork()        // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()   // sqlite 對象洩漏
                    .detectLeakedClosableObjects()  //未关闭的Closable对象泄露
                    .penaltyLog()       // 违规打印日志
                    .penaltyDeath()     // 违规崩溃
                    .build());


        }


    }
}
