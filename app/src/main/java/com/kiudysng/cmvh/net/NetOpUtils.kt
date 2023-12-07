package com.kiudysng.cmvh.net

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object NetOpUtils {
    fun getRequest(url: String, responseListener: NetResponseListener) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val openConnection = URL(url).openConnection()
                val httpUrlCon = openConnection as HttpURLConnection
                httpUrlCon.instanceFollowRedirects = false
                httpUrlCon.readTimeout = 30000
                httpUrlCon.connectTimeout = 30000
                httpUrlCon.connect()
                Log.e("tag", "--- ${httpUrlCon.responseCode}")
                responseListener.responseListener(httpUrlCon.responseCode == 302)
            } catch (e: Exception) {
                e.printStackTrace()
                responseListener.responseListener(false)
            }
        }
    }

    val EVENTS = arrayOf(
        "firstrecharger",
        "login",
        "logout",
        "registerClick",
        "rechargeClick", "register",
        "recharge", "withdrawClick", "withdrawOrderSuccess", "firstrecharge"
    )
    fun needSendFlyerEvent(event: String): Boolean {
        return listOf(*EVENTS).contains(event)
    }
}