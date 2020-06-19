package com.chencc.androidstudynotescode.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LK on 2017/9/5.
 * 
 */

public class DxJ {

    public static File jar2Dex(File aarFile) throws Exception, InterruptedException {
        File fakeDex = new File(aarFile.getParent() + File.separator + "temp");
        System.out.println("jar2Dex: aarFile.getParent(): " + aarFile.getParent());
      //��ѹaar�� fakeDex Ŀ¼��
        Zip.INSTANCE.unZip(aarFile, fakeDex);
      //�����ҵ���Ӧ��fakeDex �µ�classes.jar
        File[] files = fakeDex.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.equals("classes.jar");
            }
        });
        if (files == null || files.length <= 0) {
            throw new RuntimeException("the aar is invalidate");
        }
        File classes_jar = files[0];
       // ��classes.jar ���classes.dex
        File aarDex = new File(classes_jar.getParentFile(), "classes.dex");

      //����Ҫ��jar ת���Ϊdex ��Ҫʹ��android tools �����dx.bat
        //ʹ��java ����windows �µ�����
        DxJ.dxCommand(aarDex, classes_jar);
        return aarDex;
    }

    public static void dxCommand(File aarDex, File classes_jar) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("cmd.exe /C C:\\Users\\Administrator.USER-20200422BX\\AppData\\Local\\Android\\Sdk\\build-tools\\29.0.3\\dx --dex --output=" + aarDex.getAbsolutePath() + " " +
                classes_jar.getAbsolutePath());

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
        if (process.exitValue() != 0) {
        	InputStream inputStream = process.getErrorStream();
        	int len;
        	byte[] buffer = new byte[2048];
        	ByteArrayOutputStream bos = new ByteArrayOutputStream();
        	while((len=inputStream.read(buffer)) != -1){
        		bos.write(buffer,0,len);
        	}
        	System.out.println(new String(bos.toByteArray(),"GBK"));
            throw new RuntimeException("dx run failed");
        }
        process.destroy();
    }
}
