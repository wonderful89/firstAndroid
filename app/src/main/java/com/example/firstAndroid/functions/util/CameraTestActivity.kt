package com.example.firstAndroid.functions.util

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.base.utils.FileConstants
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

class CameraTestActivity : BaseActivity() {
    companion object {
        private const val REQUEST_CAMERA_2 = 2
        private const val REQUEST_CAMERA_THUMB = 3
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_test)
        initEvents()
    }

    private fun initEvents() {
        findViewById<TextView>(R.id.open_default_camera).setOnClickListener {
            // 1. 直接调用系统相机 没有返回值
            val intent = Intent()
            intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
            startActivity(intent);
        }
        findViewById<TextView>(R.id.open_camera_thumb).setOnClickListener {
            // 调用系统相机 有返回值 但是返回值是 缩略图
            val intent = Intent()
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA_THUMB);
        }
        findViewById<TextView>(R.id.open_camera_original).setOnClickListener {
            openCamera()
        }
    }

    /**
     *
    //       intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
    //       startActivity(intent);
    // 2 调用系统相机 有返回值 但是返回值是 缩略图
    //       intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
    //       startActivityForResult(intent, 100);
     */

    private fun openCamera() {
        val intent = Intent()
        intent.action = MediaStore.ACTION_IMAGE_CAPTURE
//        val imageFileUri = Uri.fromFile(getOutFile(TYPE_FILE_IMAGE));  //getOutFileUri(TYPE_FILE_IMAGE) //得到一个File Uri

//        val mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.png";
        Log.e(UtilTestActivity.tag, "cameraImagePath = $cameraImagePath")
        val uri = Uri.fromFile(File(cameraImagePath))
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, REQUEST_CAMERA_2)
    }

    private val cameraImagePath get() = FileConstants.APP_PATH + "/image1.png"

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == REQUEST_CAMERA_2) {
                var fis: FileInputStream? = null
                try {
                    fis = FileInputStream(cameraImagePath) // 根据路径获取数据
                    val bitmap = BitmapFactory.decodeStream(fis)
                    findViewById<ImageView>(R.id.preview_image_view).setImageBitmap(bitmap) // 显示图片
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } finally {
                    try {
                        fis?.close() // 关闭流
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            } else if (requestCode == REQUEST_CAMERA_THUMB) {
                val mImageBitmap = data?.extras?.get("data") as Bitmap?
//                val mImageBitmap = extras["data"] as Bitmap?
                findViewById<ImageView>(R.id.preview_image_view).setImageBitmap(mImageBitmap) // 显示图片
            }
        }
    }
}