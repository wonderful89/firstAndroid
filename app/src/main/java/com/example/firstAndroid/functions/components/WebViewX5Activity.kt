package com.example.firstAndroid.functions.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firstAndroid.R
import com.example.firstAndroid.databinding.ActivityWebViewX5Binding
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebView
//import kotlinx.android.synthetic.main.activity_web_view_x5.*
//import kotlinx.android.synthetic.main.activity_web_view_x5.progressbar
//import kotlinx.android.synthetic.main.activity_web_view_x5.webView

class WebViewX5Activity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewX5Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_web_view_x5)
        binding = ActivityWebViewX5Binding.inflate(layoutInflater)
        setContentView(binding.root)
        configWebView()

        binding.buttonX51.setOnClickListener {
            binding.webView.evaluateJavascript(
                "console.log('hello from native!!');(function() { return \"this\"; })();",
                ValueCallback<String?> { s ->
                    Log.d("LogName", s) // Prints 'this'
                })
        }

        binding.buttonX52.setOnClickListener {
            binding.webView.loadUrl("javascript:printLog('222')")
        }
//        loadHtmlCode()
        loadAsset()
    }

    private fun loadAsset() {
        binding.webView.loadUrl("file:///android_asset/html/debug.html");
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadUrl(){
//        val url = "https://baidu.com";
        val url = "http://www.useragentstring.com/";
        binding.webView.loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configWebView() {
        binding.webView.webChromeClient = webChromeClient
        binding.webView.webViewClient = webViewClient

        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true //允许使用js

        android.webkit.WebView.setWebContentsDebuggingEnabled(true) // 开启调试

        webSettings.cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE //不使用缓存，只从网络获取数据.
        //支持屏幕缩放
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true

        binding.webView.addJavascriptInterface(WebViewX5Activity.JSInterface(), "bridgeName")
    }


    private class JSInterface {
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        fun showToast(message: String?) {
            Log.d("JSInterface2", "2 showToast: $message")
        }
    }


    private val webChromeClient: WebChromeClient = object : WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        override fun onJsAlert(
            p0: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            val localBuilder: AlertDialog.Builder = AlertDialog.Builder(binding.webView.context)
            localBuilder.setMessage(message).setPositiveButton("确定", null)
            localBuilder.setCancelable(false)
            localBuilder.create().show()

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result?.confirm()
            return true
        }

        //获取网页标题
        override fun onReceivedTitle(p0: WebView?, title: String?) {
//            super.onReceivedTitle(view, title)
            Log.i(WebViewOriginActivity.tag, "网页标题:$title")
        }

        //加载进度回调
        override fun onProgressChanged(p0: WebView?, newProgress: Int) {
            binding.progressbar.progress = newProgress
        }
    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private val webViewClient: WebViewClient = object : WebViewClient() {
        override fun onPageFinished(p0: WebView?, url: String?) { //页面加载完成
            binding.progressbar.visibility = View.GONE
        }

        override fun onPageStarted(p0: WebView?, url: String?, favicon: Bitmap?) { //页面开始加载
            binding.progressbar.visibility = View.VISIBLE
        }

        override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
            return super.shouldOverrideUrlLoading(p0, p1)
        }

    }

    private fun loadHtmlCode(){
        binding.webView.loadDataWithBaseURL(null,"<html><head><title> 欢迎您 </title></head>" +
                "<body><h2>使用X5 webView显示 html代码</h2></body></html>", "text/html" , "utf-8", null);
    }
}