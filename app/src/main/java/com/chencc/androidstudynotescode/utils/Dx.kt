package com.chencc.androidstudynotescode.utils

import java.io.*
import java.nio.charset.Charset

object Dx {

    /**
     * jar 转 dex
     */
    fun jar2Dex(aarFile : File) : File {
        var fakeDex = File(aarFile.parent + File.separator + "aar")
        if (!fakeDex.exists()){
            fakeDex.mkdirs()
        } else {
            val files = fakeDex.listFiles()
            files.forEach {
                it.delete()
            }
        }

        println("fakeDex : ${fakeDex.path}")
        // 先解压 aar
        Zip.unZip(aarFile, fakeDex)
        // 过滤找到对应的fakeDex 下的classes.jar
        var files = fakeDex.listFiles(FilenameFilter{ _, it ->
            it == "classes.jar"
        })

        if (files == null || files.isEmpty()){
            throw RuntimeException("the aar is invalidate")
        }
        var classes_jar = files[0]
        //将 classes.jar  变成 classes.dex
        var aarDex = File(classes_jar.parentFile, "classes.dex")

        //我们要将jar 转变成为dex 需要使用android tools 里面的dx.bat
        //使用java 调用windows 下的命令
        dxCommand(aarDex, classes_jar);
        return aarDex
    }

    /**
     * 执行 windows 命令  使用 dx.bat 脚本  将 jar包转换为 dex
     */
    fun dxCommand(aarDex: File, classesJar: File) {
        var runtime = Runtime.getRuntime()
         val process = runtime.exec(
             "cmd.exe /C C:/Users/Administrator.USER-20200422BX/AppData/Local/Android/Sdk/build-tools/29.0.3/dx --dex --output=" + aarDex.absolutePath.toString() + " " +
                     classesJar.absolutePath.toString()
         )

//         val process = runtime.exec(
//             "cmd.exe /C  C:\\Users\\Administrator.USER-20200422BX\\AppData\\Local\\Android\\Sdk\\build-tools\\29.0.3\\dx --dex --output=  E:\\project\\pp\\git\\AndroidStudyNotesCode\\app\\src\\main\\source\\aar\\classes.dex     E:\\project\\pp\\git\\AndroidStudyNotesCode\\app\\src\\main\\source\\aar\\classes.jar"
//         )

         read(process.getInputStream(), System.out);
         read(process.getErrorStream(), System.err);

        println("${aarDex.absolutePath}     ${classesJar.absolutePath.toString()}"  )
         try {
             var exitCode  = process.waitFor()
             println("process  : exitCode ${exitCode}")
         } catch (e: InterruptedException) {
             e.printStackTrace()
             println("process  : ${e.message}")
         }

         println("process  : exitValue ${process.exitValue()}")
         if (process.exitValue() != 0){
             println("process  : exitValue")
             var inputStream = process.errorStream
             var len = 0
             var buffer = ByteArray(2048)
             var bos = ByteArrayOutputStream()
             while (inputStream.read(buffer).also { len = it } != -1){
                 bos.write(buffer, 0, len)
             }
             println("process :  "+ String(bos.toByteArray(), Charset.forName("GBK")))
             throw  RuntimeException("dx run failed");
         }

        process.destroy()
     }


    // 读取输入流
     fun read(inputStream: InputStream, out: PrintStream) {
         var fos = FileOutputStream(File("app/src/main/source/log.txt"))
        try {
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String = ""
            var bos = ByteArrayOutputStream()
            while (reader.readLine().also { it?.let {  line = it} } != null) {
                out.println(line)
            }
            println("process :  "+ String(bos.toByteArray(), Charset.forName("GBK")))
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fos.close()
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}