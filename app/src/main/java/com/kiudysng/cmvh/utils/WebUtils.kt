package com.kiudysng.cmvh.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.JavascriptInterface
import com.appsflyer.AppsFlyerLib
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kiudysng.cmvh.ui.activity.WebViewActivity


class WebUtils(val activity: Activity) {
    @JavascriptInterface
    fun openWebView(url: String) {
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        activity.startActivityForResult(intent, 1002)
    }

    @JavascriptInterface
    fun closeWebView() {
        activity.finish()
    }

    @JavascriptInterface
    fun openAndroid(url: String) {
        try {
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                val chooser = Intent.createChooser(this, "Choose a browser")
                activity.startActivity(chooser)
            }
        } catch (e: Exception) {
            Log.e("xxxTag", "openSystemBrowser failure $e")
        }
    }

    @JavascriptInterface
    fun eventTracker(eventType: String, eventValues: String) {
        if (eventValues.isNotEmpty()) {
            val gson = Gson()
            val type = object : TypeToken<Map<String?, String>>() {}.type
            val map = gson.fromJson<Map<String, String>>(eventValues, type)
            AppsFlyerLib.getInstance().logEvent(activity, eventType, map)
        }
    }

}