package com.kiudysng.cmvh.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import com.kiudysng.cmvh.databinding.ActivityWebviewBinding
import com.kiudysng.cmvh.utils.ChromeClients
import com.kiudysng.cmvh.utils.WebUtils


class WebViewActivity : AppCompatActivity() {
    val binding: ActivityWebviewBinding by lazy {
        ActivityWebviewBinding.inflate(layoutInflater)
    }
    var webView: WebView? = null
    var jump: Boolean = false
    var url: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        supportActionBar?.hide() // 隐藏默认的操作栏
        setContentView(binding.root)
        val bundle = intent.extras
        if (bundle != null) {
            url = bundle.getString("url")
            jump = bundle.getBoolean("jump")
        }
        if (url.isNullOrEmpty()) {
            //跳转进入首页
            finishWeb()
        }

        binding.apply {
            wWebRoot.visibility = if (jump) View.GONE else View.VISIBLE
            webView = if (jump) fWeb else wWeb
            webView?.visibility = View.VISIBLE
            imgClose.setOnClickListener {
                finishWeb()
            }
            initWebView(webView!!)
            Log.e("pLog","onCreate---------$url")
            webView?.loadUrl(url!!)
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(webView: WebView) {
        webView.settings.useWideViewPort = true
        webView.settings.defaultTextEncodingName = "utf-8"
        webView.settings.savePassword = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportMultipleWindows(true)
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.setSupportMultipleWindows(true)
        webView.settings.allowFileAccess = true
        webView.isHorizontalScrollBarEnabled = false
        webView.isVerticalScrollBarEnabled = false
        webView.addJavascriptInterface(WebUtils(this), "Android")
        webView.webChromeClient = ChromeClients(this,webView)
        webView.setDownloadListener { str, str2, str3, str4, j2 ->
            WebUtils(this).openWebView(str)
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.e("pLog","onPageFinished---- $url")
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return onKeyDown()
    }

    private fun finishWeb() {
        setResult(-2)
        finish()
    }

    fun onKeyDown(): Boolean {
        return if (webView == null) {
            finish()
            true
        } else if (webView!!.canGoBack()) {
            this.webView?.goBack()
            true
        } else if (webView!!.size > 0) {
//            (findViewById<View>() as FrameLayout).removeView(webView)
//            val aab2: WebView = this.f3845aaz.aab()
//            this.webView = aab2
//            if (aab2 != null) {
//                aab2.visibility = 0
//                this.f3844aay.aaf(this.f3841aav)
//                return true
//            }
            finish()
            true
        } else {
            finish()
            true
        }
    }

}