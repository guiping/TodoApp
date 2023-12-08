package com.kiudysng.cmvh.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.kiudysng.cmvh.databinding.ActivityWebviewBinding
import com.kiudysng.cmvh.net.NetOpUtils
import com.kiudysng.cmvh.utils.ChromeClients
import com.kiudysng.cmvh.utils.WebUtils
import org.json.JSONObject


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
        Log.e("pLog", "webActivity p---- jump = $jump")

        binding.apply {
            wWebRoot.visibility = if (jump) View.GONE else View.VISIBLE
            webView = if (jump) fWeb else wWeb
            webView?.visibility = View.VISIBLE
            imgClose.setOnClickListener {
                finishWeb()
            }
            initWebView(webView!!)
            Log.e("pLog", "onCreate---------$url")
            webView?.loadUrl(url!!)
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(webVie: WebView) {
        val webSettings: WebSettings =  webVie.getSettings()
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webSettings.setSupportZoom(true)
        webSettings.useWideViewPort = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSettings.displayZoomControls = true
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webVie.isHorizontalScrollBarEnabled = false
        webVie.isVerticalScrollBarEnabled = false
        webVie.addJavascriptInterface(object : Any() {
            @JavascriptInterface
            fun postMessage(tag: String, value: String) {
                Log.e("pLog", "postMessage  jsBridge    -- tag = $tag  value = $value")
                when (tag) {
                    "openWindow" -> {
                        try {
                            val jsonObject = JSONObject(value)
                            val url1 = jsonObject.optString("url")
                            WebUtils.openWebView(this@WebViewActivity, url1)
                        } catch (e: Exception) {

                        }
                    }
                    "closeWindow" -> {
                        this@WebViewActivity.finish()
                    }

                    else -> {
                        if (NetOpUtils.needSendFlyerEvent(tag)) {
                            WebUtils.logEvent(this@WebViewActivity, tag, value)
                        }
                    }
                }
            }
        }, "jsBridge")
//        webVie.webChromeClient = ChromeClients(this, webVie)
        webVie.setDownloadListener { str, str2, str3, str4, j2 ->
            val i = Intent("android.intent.action.VIEW")
            i.data = Uri.parse(url)
            this@WebViewActivity.startActivity(i)
        }
        webVie.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.e("pLog", "onPageFinished---- $url")
            }
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                if (request.url.toString().startsWith("http") || request.url.toString()
                        .startsWith("https")
                ) {
                    if (!request.url.toString()
                            .contains("https://landing-page.cdn-dysxb.com/8k8/") && !request.url.toString()
                            .contains("https://t.me/cskh_8k8")
                    ) {
                        this@WebViewActivity.webView?.loadUrl(request.url.toString())
                        return true
                    }

                    val i = Intent("android.intent.action.VIEW")
                    i.data = Uri.parse(request.url.toString())
                    this@WebViewActivity.startActivity(i)
                    return true
                }
                return true
            }
        }
    }


    private fun finishWeb() {
        setResult(-2)
        finish()
    }

    // androidx.activity.ComponentActivity, android.app.Activity
    override fun onBackPressed() {
        if (this.webView?.canGoBack() == true) {
            this.webView?.goBack()
        } else {
            finish()
        }
    }

}