package com.kiudysng.cmvh.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.net.http.SslError
import android.os.Message
import android.util.Log
import android.webkit.*
import android.webkit.WebView.WebViewTransport

class ChromeClients(var activity: Activity, var webView: WebView, private val type: Int) :
    WebChromeClient() {
    private val TAB = "ChromeClients"
    override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
        if (type != 1) {
            return true
        }
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(message)
            .setPositiveButton("OK") { arg0: DialogInterface, arg1: Int -> arg0.dismiss() }.show()
        result.cancel()
        return true
    }

    override fun onCreateWindow(
        view: WebView,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message
    ): Boolean {
        val tempWebView = WebView(activity)
        tempWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                Log.e("ppLog", "请求地址 ---- \${}" + request.url)

                return true
            }

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed()
            }
        }
        val transport = resultMsg.obj as WebViewTransport
        transport.webView = tempWebView
        resultMsg.sendToTarget()
        return true
    }
}