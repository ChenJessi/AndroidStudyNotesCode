package com.chencc.androidstudynotescode.utils

import android.annotation.SuppressLint
import java.io.File
import java.io.FileOutputStream
import java.io.FilenameFilter
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec

@SuppressLint("GetInstance")
object AESUtils {
    const val DEFAULT_PWD = "abcdefghijklmnop"

    private const val algorithmStr = "AES/ECB/PKCS5Padding"

    private lateinit var encryptCipher: Cipher
    private lateinit var decryptCipher: Cipher


    fun init(password: String) {
        try {
            encryptCipher = Cipher.getInstance(algorithmStr)
            decryptCipher = Cipher.getInstance(algorithmStr)

            var keyStr = password.toByteArray()
            var key = SecretKeySpec(keyStr, "AES")
            encryptCipher.init(Cipher.ENCRYPT_MODE, key)
            decryptCipher.init(Cipher.DECRYPT_MODE, key)
        } catch (e : NoSuchAlgorithmException) {
            e.printStackTrace();
        } catch (e : NoSuchPaddingException) {
            e.printStackTrace();
        } catch (e : InvalidKeyException) {
            e.printStackTrace();
        }
    }

    /**
     * @param srcApkFile 源文件
     * @param dstApkFile  目标文件
     * @return  加密后的 新dex 文件
     */

    fun encryptAPKFile(srcApkFile : File, dstApkFile : File) : File?{
        // 解压 apk到 dstApkFile
        Zip.unZip(srcApkFile, dstApkFile)
        // 获取所有 dex 文件
        var dexFiles = dstApkFile.listFiles(FilenameFilter { _, name ->
            name.endsWith(".dex")
        })

        var mainDexFile : File? = null

        dexFiles?.forEach { dexFile->
            // 读取 dex 文件数据
            var buffer = Utils.getBytes(dexFile)
            // 加密
            var encryptBytes = encrypt(buffer)

            if (dexFile.name.endsWith("classes.dex")){
                mainDexFile = dexFile
            }
            // 将加密后的数据写入 替换原来的数据
            var fos = FileOutputStream(dexFile)
            fos.write(encryptBytes)

            fos.flush()
            fos.close()
        }

        return mainDexFile
    }



    fun encrypt(content : ByteArray) : ByteArray?{
        try {
            var result = encryptCipher.doFinal(content)
            return result
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        } catch (e : BadPaddingException){
            e.printStackTrace()
        }
        return null
    }

}