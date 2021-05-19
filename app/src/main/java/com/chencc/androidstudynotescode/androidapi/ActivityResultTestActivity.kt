package com.chencc.androidstudynotescode.androidapi

import android.Manifest.permission.*
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.databinding.ActivityActivityResultSecondTestBinding
import com.chencc.androidstudynotescode.databinding.ActivityActivityResultTestBinding
import com.chencc.androidstudynotescode.utils.UriUtils
import com.jessi.arouter_annotation_java.ARouter
import com.jessi.arouter_annotation_java.Parameter
import com.jessi.arouter_api_java.ParameterManager
import java.io.File

/**
 *
 * Activity Result API测试
 *
 *
 */
private const val TAG = "ActivityResultActivity"

@ARouter(path = "/result/ActivityResultTestActivity", group = "result")
class ActivityResultTestActivity : AppCompatActivity() {

    @JvmField
    @Parameter
    var stringKey = ""
    @JvmField
    @Parameter
    public var intKey = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityActivityResultTestBinding>(this , R.layout.activity_activity_result_test)
        ParameterManager.getInstance().loadParameter(this)

//        val str = intent.getStringExtra("StringKey")
//        val i = intent.getIntExtra("intKey", 0)
        Log.e(TAG, "intent test:   $stringKey  $intKey" )

        binding.button.setOnClickListener {
//            takePicturePreview()      // 拍照，返回 bitmap
//            requestPermission()       // 单个权限请求
//            requestMultiplePermissions()      //单个权限请求
//            pickContact()        //  多个权限请求
//            openDocument()          // 选择文件
//            getContent()          // 选择文件
//            createDocument()          // 创建文件
//            getMultipleContents()          // 选取多个文件
//            openDocumentTree()          //  打开文件夹
//            openMultipleDocuments()          //  打开多种类型文件
//            takeVideo()          //  录制视频
            startActivityForResult()    // activity 跳转

        }
    }

    /**
     *  拍照，返回 bitmap
     */
    fun takePicturePreview(){
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
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
        }.launch("image/*")
    }

    /**
     * 创建文件
     */
    fun createDocument(){
        registerForActivityResult(ActivityResultContracts.CreateDocument()){
            Log.e(TAG, "createDocument:  uri : $it  uri ${it.path}" )
        }.launch("文件名.txt")
    }

    /**
     * 选择多个文件
     */
    fun getMultipleContents(){
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()){
            Log.e(TAG, "getMultipleContents:  ${it.toString()}")
        }.launch("image/*")
    }

    /**
     * 选择文件夹
     */
    fun openDocumentTree(){
        registerForActivityResult(ActivityResultContracts.OpenDocumentTree()){
            Log.e(TAG, "openDocumentTree:  : $it ")
        }.launch(null)
    }

    /**
     * 打开多种类型文件
     */
    fun openMultipleDocuments(){
        registerForActivityResult(ActivityResultContracts.OpenMultipleDocuments()){
            Log.e(TAG, "openMultipleDocuments: $it" )
        }.launch(arrayOf("image/*"))
    }

    /**
     * 录制视频
     */
    fun takeVideo(){
//        val file1 = File(cacheDir.absolutePath + "/images")
//        file1.mkdirs()
        val file = File(cacheDir.absolutePath+"/images", "test.mp4")
//        file.createNewFile()
        val uri = FileProvider.getUriForFile(this@ActivityResultTestActivity, "com.chencc.androidstudynotescode.fileprovider", file)
        registerForActivityResult(ActivityResultContracts.TakeVideo()){
            // 返回缩略图，可能为null
            Log.e(TAG, "takeVideo:  $it")
        }.launch(uri)
    }

    /**
     * activity 跳转
     */
    fun startActivityForResult(){



        var selectionIntent = Intent(Intent.ACTION_PICK, null).apply {
            type = "image/*"
        }

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val intentArray = arrayOf<Intent>(cameraIntent)
        val chooserIntent = Intent(Intent.ACTION_CHOOSER).apply {
            putExtra(Intent.EXTRA_TITLE, "文件选择")
            putExtra(Intent.EXTRA_INTENT, selectionIntent)
            putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
        }

        val intent = Intent(this@ActivityResultTestActivity, ActivityResultSecondTestActivity::class.java)
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            Log.e(TAG, "startActivityForResult:  uri : ${it.data?.dataString}  :  ${UriUtils.getPath(this@ActivityResultTestActivity, Uri.parse(it.data?.dataString))}")
        }.launch(chooserIntent)
    }
}


const val INTENT_KEY = "INTENT_KEY"

class ActivityResultSecondTestActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityActivityResultSecondTestBinding>(this , R.layout.activity_activity_result_second_test)
    }

    override fun onBackPressed() {

        val intent = Intent().apply {
            putExtra(INTENT_KEY,"ces ==========")
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}