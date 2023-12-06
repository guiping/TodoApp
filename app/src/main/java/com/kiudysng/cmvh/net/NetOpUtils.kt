package com.kiudysng.cmvh.net

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

object NetOpUtils {
    fun getRequest(url: String, responseListener: NetResponseListener) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val openConnection = URL(url).openConnection()
                val httpUrlCon = openConnection as HttpURLConnection
                httpUrlCon.instanceFollowRedirects = false
                httpUrlCon.readTimeout = 10000
                httpUrlCon.connectTimeout = 10000
                httpUrlCon.connect()
                Log.e("tag", "--- ${httpUrlCon.responseCode}")
                responseListener.responseListener(httpUrlCon.responseCode == 302)
            } catch (e: Exception) {
                e.printStackTrace()
                responseListener.responseListener(false)
            }
        }
    }
}