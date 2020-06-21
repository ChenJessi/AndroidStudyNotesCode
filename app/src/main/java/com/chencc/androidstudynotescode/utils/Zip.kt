@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "UNREACHABLE_CODE")

package com.chencc.androidstudynotescode.utils

import java.io.*
import java.util.zip.*
import kotlin.Exception

/**
 * 压缩解压工具
 */
object Zip {


    @Throws(Exception::class)
    fun zip(dir: File, zip: File) {
        zip.delete()
        // 对输出文件做CRC32校验

        var cos = CheckedOutputStream(FileOutputStream(zip), CRC32())
        var zos = ZipOutputStream(cos)
        println("basePath : ${dir.parentFile.path + File.separator}")
        compress(dir, zos, "")
        zos.flush()
        zos.close()
    }

    @Throws(Exception::class)
    private fun compress(srcFile: File, zos: ZipOutputStream, basePath: String) {
        if (srcFile.isDirectory) {
            compressDir(srcFile, zos, basePath)
        } else {
            compressFile(srcFile,zos , basePath)
        }
    }


    /**
     * 解压
     * @param zip 源文件
     * @param dir 目标文件
     */
    @Throws(Exception::class)
    fun unZip(zip: File, dir: File){
        dir.delete()
        var zipFile = ZipFile(zip)
        var entries = zipFile.entries()
        while (entries.hasMoreElements()){
            val zipEntry = entries.nextElement()
            var name = zipEntry.name
            if (name == "META-INF/CERT.RSA" || name == "META-INF/CERT.SF" || name == "META-INF/MANIFEST.MF"){
                continue
            }
            if (!zipEntry.isDirectory){
                var file = File(dir, name)
                if (!file.parentFile.exists()){
                    file.parentFile.mkdirs()
                }
                var fos = FileOutputStream(file)
                var ips = zipFile.getInputStream(zipEntry)

                var buffer = ByteArray(1024)
                var let = 0
                while (ips.read(buffer, 0, 1024).let {
                        let = it
                        it != -1
                    }){
                    fos.write(buffer, 0, let)
                }
                ips.close()
                fos.close()
            }
        }
        zipFile.close()
    }


    @Throws(Exception::class)
    private fun compressFile(file: File, zos: ZipOutputStream, dir : String) {
        var dirName = dir + file.name

        var dirNameNew = dirName.split("/")

        var buffer = StringBuffer()

        if (dirNameNew.size > 1){
            for (str in dirNameNew){
                if (str == "temp"){
                    return continue
                }
                buffer.append("/")
                buffer.append(str)
            }
        } else {
            buffer.append("/");
        }

        println("buffer  : $buffer")

        var entry = ZipEntry(buffer.toString().substring(1))
        zos.putNextEntry(entry)
        var bis = BufferedInputStream(FileInputStream(file))

        var count = 0;

        val data = ByteArray(1024)

        while (bis.read(data, 0, 1024).let {
                count = it
                it !=- 1 }){
            zos.write(data, 0, count);
        }
        bis.close()
        zos.closeEntry()
    }

    @Throws(Exception::class)
    private fun compressDir(dir: File, zos: ZipOutputStream, basePath: String){
        var files = dir.listFiles()
        //构建空目录
        if (files.isEmpty()){
            //新建一个 zip 文件
            println("ZipEntry  :  $basePath${dir.name}/")
            var entry = ZipEntry("$basePath/")
            zos.putNextEntry(entry)
            zos.closeEntry();
        }
        for (file in files){
            //递归压缩
            println("name   :  ${dir.name}")
            if (dir.name == "temp"){
                compress(file, zos, "$basePath${dir.name}/")
            } else {
                compress(file, zos, "$basePath${dir.name}/")
            }

        }
    }

}