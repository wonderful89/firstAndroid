package com.example.firstAndroid.functions.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firstAndroid.R
import com.example.firstAndroid.databinding.ActivityWebViewOriginBinding

//import kotlinx.android.synthetic.main.activity_web_view_origin.*

class WebViewOriginActivity : AppCompatActivity() {

    companion object {
        val tag = "WebViewOriginActivity";
    }

    private lateinit var binding: ActivityWebViewOriginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_web_view_origin)
        binding = ActivityWebViewOriginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configWebView()

        binding.button.setOnClickListener {
            binding.webView.evaluateJavascript(
                "console.log('hello from native!!');(function() { return \"this\"; })();",
                ValueCallback<String?> { s ->
                    Log.d("LogName", s) // Prints 'this'
                })
        }

        binding.button2.setOnClickListener {
            binding.webView.loadUrl("javascript:printLog('111')")
        }

        loadAsset()
//        loadUrl()
    }

    private fun loadAsset() {
        binding.webView.loadUrl("file:///android_asset/html/debug.html");
    }

    private fun loadUrl() {
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

        WebView.setWebContentsDebuggingEnabled(true) // 开启调试

        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE //不使用缓存，只从网络获取数据.
        //支持屏幕缩放
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true

        binding.webView.addJavascriptInterface(JSInterface(), "bridgeName")
    }

    private class JSInterface {
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        fun showToast(message: String?) {

            Log.d("JSInterface", "showToast: $message")
//            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun loadHtmlCode() {
        binding.webView.loadDataWithBaseURL(
            null, "<html><head><title> 欢迎您 </title></head>" +
                    "<body><h2>使用webview显示 html代码</h2></body></html>", "text/html", "utf-8", null
        );
    }

    private val webChromeClient: WebChromeClient = object : WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        override fun onJsAlert(
            webView: WebView,
            url: String,
            message: String,
            result: JsResult
        ): Boolean {
            Log.d(tag, "onJsAlert: $message")
            val localBuilder: AlertDialog.Builder = AlertDialog.Builder(webView.context)
            localBuilder.setMessage(message).setPositiveButton("确定", null)
            localBuilder.setCancelable(false)
            localBuilder.create().show()

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm()
            return true
        }

        //获取网页标题
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            Log.i(tag, "网页标题:$title")
        }

        //加载进度回调
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            binding.progressbar.progress = newProgress
        }

    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private val webViewClient: WebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) { //页面加载完成
            binding.progressbar.visibility = View.GONE
        }

        override fun onPageStarted(
            view: WebView,
            url: String,
            favicon: Bitmap?
        ) { //页面开始加载
            binding.progressbar.visibility = View.VISIBLE
            super.onPageStarted(view, url, favicon)
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.d(tag, "拦截url:$url")
            if (url == "http://www.google.com/") {
                Toast.makeText(this@WebViewOriginActivity, "国内不能访问google,拦截该url", Toast.LENGTH_LONG)
                    .show()
                return true //表示我已经处理过了
            }
            val should = super.shouldOverrideUrlLoading(view, url)
            Log.d(tag, "should: $should")
            return should;
        }
    }
}