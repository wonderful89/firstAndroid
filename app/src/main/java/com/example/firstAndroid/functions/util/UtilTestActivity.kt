package com.example.firstAndroid.functions.util

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import com.example.firstAndroid.R
import com.example.firstAndroid.base.BaseActivity
import com.example.firstAndroid.base.utils.FileConstants
import com.example.firstAndroid.databinding.ActivityTestListBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

//import kotlinx.android.synthetic.main.activity_test_list.*

class UtilTestActivity : BaseActivity() {
    companion object{
        const val tag = "UtilTestList"
        private const val REQUEST_CAMERA_2 = 2
    }

    private lateinit var binding: ActivityTestListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_test_list)
        binding = ActivityTestListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Permission.checkPermission(this)

        val lists = UtilTest.values().map { item -> item.title }
        val arrayAdapter2 = ArrayAdapter(this, R.layout.item_simple_list_0, lists)
        binding.listView.adapter = arrayAdapter2
        binding.listView.dividerHeight = 2
        binding.listView.setSelector(R.drawable.listview_selector_0)

        binding.listView.setOnItemClickListener { _, _, position: Int, _: Long ->
//            var intentClass: Class<AppCompatActivity>? = null
//            intentClass = when (position) {
//                else -> MainActivity().javaClass
//            }
//
//            val intent = android.content.Intent(this, intentClass)
//            startActivity(intent)
            when(lists[position]) {
                "相机测试" -> {
                    openCamera()
                }
                else -> {

                }
            }
        }
    }

    private fun openCamera() {
        val intent = Intent()
        intent.action = MediaStore.ACTION_IMAGE_CAPTURE
//        val imageFileUri = Uri.fromFile(getOutFile(TYPE_FILE_IMAGE));  //getOutFileUri(TYPE_FILE_IMAGE) //得到一个File Uri

        Log.e(tag, "cameraImagePath = $cameraImagePath")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, File(cameraImagePath))
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
//                    ivShowPicture.setImageBitmap(bitmap) // 显示图片
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } finally {
                    try {
                        fis?.close() // 关闭流
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}