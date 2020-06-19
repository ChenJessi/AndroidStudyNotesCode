package com.chencc.androidstudynotescode.utils

import android.annotation.SuppressLint
import java.io.File
import java.lang.Exception
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
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
    fun encryptAPKFile(srcApkFile : File, dstApkFile : File){
        Zip.zip(srcApkFile, dstApkFile)
    }
}