package com.example.firstAndroid.functions.logic.download

//import android.app.DownloadManager
import android.content.Context
import com.example.firstAndroid.functions.logic.download.downloader.DownloadManager

//import android.content.Context.DOWNLOAD_SERVICE
//import androidx.core.content.ContextCompat.getSystemService

object DownloadManagerWrap {
    lateinit var appContext: Context
    fun initDownloadManager() {
        // 设置可同时下载任务的数量
        DownloadManager.initDownloader(appContext)
        DownloadManager.setCorePoolSize(3)
        DownloadManager.setMaxPoolSize(30)

    }
}