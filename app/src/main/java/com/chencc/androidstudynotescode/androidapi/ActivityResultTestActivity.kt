package com.chencc.androidstudynotescode.androidapi

import android.Manifest.permission.*
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.databinding.ActivityActivityResultSecondTestBinding
import com.chencc.androidstudynotescode.databinding.ActivityActivityResultTestBinding
import java.io.File
import java.net.URI

/**
 *
 * Activity Result API测试
 *
 *
 */
private const val TAG = "ActivityResultActivity"
class ActivityResultTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityActivityResultTestBinding>(this , R.layout.activity_activity_result_test)
        binding.button.setOnClickListener {
//            takePicturePreview()      // 拍照，返回 bitmap
//            requestPermission()       // 单个权限请求
//            requestMultiplePermissions()      //单个权限请求
//            pickContact()        //  多个权限请求
//            openDocument()          // 选择文件
            getContent()          // 选择文件
        }
    }

    /**
     *  拍照，返回 bitmap
     */
    fun takePicturePreview(){
        registerForActivityResult(object : ActivityResultContracts.TakePicturePreview(){
        }) {
            Log.e(TAG, "test:   ${it}")
        }.launch(null)

    }

    /**
     * 单个权限请求
     */
    fun requestPermission(){
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it){
                // 同意权限
                Log.e(TAG, "requestPermission:  同意权限")
            }else{
                // 拒绝权限
                Log.e(TAG, "requestPermission:  拒绝权限")
            }
        }.launch(WRITE_EXTERNAL_STORAGE)
    }

    /**
     * 多个权限请求
     */
    fun requestMultiplePermissions(){
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            /**
             * 注意：已同意的权限再次，申请有可能不返回
             */
            it.forEach { entry ->
                Log.e(TAG, "requestMultiplePermissions: ${it.size}    key : ${entry.key}  value : ${entry.value}" )
            }


            if (it[WRITE_EXTERNAL_STORAGE] == false){
                // 拒绝权限
            }else{
                // 同意权限
            }
        }.launch(arrayOf(WRITE_EXTERNAL_STORAGE, CAMERA, READ_SMS))
    }
    /**
     * 查询联系人信息
     */
    fun pickContact(){
        registerForActivityResult(ActivityResultContracts.PickContact()) {
            if (it != null){
//                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                val cursor = contentResolver.query(it, null, null, null, null)?.apply {
                    if (moveToFirst()){
                        val name = getString(getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        val id = getString(getColumnIndex(ContactsContract.Contacts._ID))

                        Log.e(TAG, "pickContact:   id : $id   name : $name ")

//                        val displayName: String = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//                        val number: String = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//                        Log.e(TAG, "pickContact:   displayName : $displayName  number : $number")


                    }
                }
            }
        }.launch(null)
    }

    /**
     * 打开文件
     */
    fun openDocument(){
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            Log.e(TAG, "openDocument:  uri : $it")
        }.launch(arrayOf("image/*", "text/plain"))
    }

    /**
     * 选择文件
     */
    fun getContent(){
        registerForActivityResult(ActivityResultContracts.GetContent()){
            Log.e(TAG, "getContent:  uri : $it  uri ${it.path}" )
            val file = File(URI(it.toString()))
            Log.e(TAG, "getContent:  file : ${file.exists()}  ${file.absolutePath}" )
        }.launch("image/*")
    }

    fun a(){
        registerForActivityResult(ActivityResultContracts.CreateDocument()){

        }.launch("")
    }
}


const val INTENT_KEY = "INTENT_KEY"

class ActivityResultSecondTestActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityActivityResultSecondTestBinding>(this , R.layout.activity_activity_result_test)
    }

    override fun onBackPressed() {

        val intent = Intent().apply {
            putExtra(INTENT_KEY,"ces ==========")
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}