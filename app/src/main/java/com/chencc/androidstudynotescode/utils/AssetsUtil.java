package com.chencc.androidstudynotescode.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * assets Ŀ¼�ļ�������
 */
public class AssetsUtil {
    private static final String TAG = "AssetsUtil";
    /**
     * ��ȡassetsĿ¼�µ�ͼƬ
     * @param context ������
     * @param fileName  �ļ���
     * @return  BitmapͼƬ
     */
    public static Bitmap getImageFromAssetsFile(Context context, String fileName){
        Bitmap bitmap = null;
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(fileName);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * ��ȡassetsĿ¼�µĵ����ļ�
     * ���ַ�ʽֻ������webview����
     * ��ȡ�ļ��У�ֱ��ȡ·���ǲ��е�
     * @param context ������
     * @param fileName  �ļ�����
     * @return File
     */
    public static File getFileFromAssetsFile(Context context, String fileName){
        String path = "file:///android_asset/" + fileName;
        File file = new File(path);
        return file;
    }

    /**
     * ��ȡassetsĿ¼�������ļ�
     * @param context  ������
     * @param path  �ļ���ַ
     * @return files[] �ļ��б�
     */
    public static String[] getFilesFromAssets(Context context, String path){
        AssetManager assetManager = context.getAssets();
        String files[] = null;
        try {
            files = assetManager.list(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String str : files) {
//            LogUtils.logInfoStar(str);
            Log.v(TAG, "assets files -- " + str);
        }

        return files;
    }

    /**
     * ��assets�µ��ļ��ŵ�sdָ��Ŀ¼��
     * @param context     ������
     * @param assetsPath  assets�µ�·��
     */
    public static void putAssetsToSDCard(Context context, String assetsPath){
        putAssetsToSDCard(context, assetsPath, context.getExternalFilesDir(null).getAbsolutePath());
    }

    /**
     * ��assets�µ��ļ��ŵ�sdָ��Ŀ¼��
     * @param context      ������
     * @param assetsPath   assets�µ�·��
     * @param sdCardPath   sd����·��
     */
    public static void putAssetsToSDCard(Context context, String assetsPath, String sdCardPath){
        AssetManager assetManager = context.getAssets();
        try {
            String files[] = assetManager.list(assetsPath);
            if (files.length == 0) {
                // ˵��assetsPathΪ��,����assetsPath��һ���ļ�
                InputStream is = assetManager.open(assetsPath);
                byte[] mByte = new byte[1024];
                int bt = 0;
                File file = new File(sdCardPath + File.separator
                        + assetsPath.substring(assetsPath.lastIndexOf('/')));
                if (!file.exists()) {
                    // �����ļ�
                    file.createNewFile();
                } else {
                    //�Ѿ�����ֱ���˳�
                    return;
                }

                // д����
                FileOutputStream fos = new FileOutputStream(file);
                // assetsΪ�ļ�,���ļ��ж�ȡ��
                while ((bt = is.read(mByte)) != -1) {
                    // д�������ļ���
                    fos.write(mByte, 0, bt);
                }

                // ˢ�»�����
                fos.flush();
                // �رն�ȡ��
                is.close();
                // �ر�д����
                fos.close();
            } else {
                // ��mString���ȴ���0,˵����Ϊ�ļ���
                sdCardPath = sdCardPath + File.separator + assetsPath;
                File file = new File(sdCardPath);
                if (!file.exists()) {
                    // ��sd�´���Ŀ¼
                    file.mkdirs();
                }

                // ���еݹ�
                for (String stringFile : files) {
                    putAssetsToSDCard(context, assetsPath + File.separator
                            + stringFile, sdCardPath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}