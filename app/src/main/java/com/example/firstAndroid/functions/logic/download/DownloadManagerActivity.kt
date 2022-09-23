package com.example.firstAndroid.functions.logic.download

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.firstAndroid.R
import com.example.firstAndroid.base.utils.FileConstants
import com.example.firstAndroid.base.utils.createOrExistFile
import com.example.firstAndroid.base.utils.createOrExistsDir
import com.example.firstAndroid.base.utils.toFile
import com.example.firstAndroid.functions.logic.download.downloader.DownloadManager
import com.example.firstAndroid.functions.logic.download.downloader.Downloader
import com.example.firstAndroid.functions.logic.download.downloader.Observer
import okhttp3.OkHttpClient
import okhttp3.internal.notify
import okhttp3.logging.HttpLoggingInterceptor
import java.util.*

class HttpLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.i("HttpLogger---", message)
    }
}

@Route(path = "/logic/download")
class DownloadManagerActivity : AppCompatActivity() {

    val tag = "DownloadManagerActivity"
    private var downloaders: MutableList<Downloader> = mutableListOf()

    init {
        DownloadManagerWrap.initDownloadManager()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_manager)

        val listView  = findViewById<RecyclerView>(R.id.listView)
        listView.adapter = downloadAdapter
        listView.layoutManager = LinearLayoutManager(this)

        findViewById<TextView>(R.id.addTask).setOnClickListener {
            addDownloadTask()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addDownloadTask() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor(HttpLogger())
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
        val taskId = UUID.randomUUID().toString()
        val filePathDir = FileConstants.APP_PATH + "/downloader"
        filePathDir.toFile().createOrExistsDir()
        // http://xcal1.vodafone.co.uk/
//        val downloadUrl = "http://212.183.159.230/200MB.zip"
        val downloadUrl = "https://dengyue-1251316161.cos.ap-beijing.myqcloud.com/xclass/landmoon/app-yingyongbao-release_4.0.0_2_20220815_16_15.apk"
//        val downloadUrl = "http://212.183.159.230/100MB.zip"

        Log.i(tag, "filePathDir = $filePathDir")
        val downloader: Downloader = Downloader.Builder()
            .client(client)
            .fileName("$taskId.apk")
            .filePath(filePathDir)
            .taskId("$taskId")
            .url("$downloadUrl")
            .build()
        downloaders.add(downloader)
        DownloadManager.addDownloader(downloader)
        DownloadManager.start(taskId)

//        downloadAdapter.notifyDataSetChanged()
        downloadAdapter.notifyItemInserted(downloaders.size - 1)
    }

    private var downloadAdapter = DownloadTaskAdapter(downloaders) { pos, item ->

    }
}