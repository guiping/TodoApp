package com.kiudysng.cmvh.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Message
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebView.WebViewTransport


class ChromeClients(var activity: Activity, var webView: WebView ) :
    WebChromeClient() {
    private val TAB = "ChromeClients"
    override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
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
        val newWebView = WebView(view.context)
        val transport = resultMsg.obj as WebViewTransport
        transport.webView = newWebView
        resultMsg.sendToTarget()
        return true
    }
}