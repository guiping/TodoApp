package com.kiudysng.cmvh.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kiudysng.cmvh.ui.activity.WebViewActivity
import org.json.JSONObject


object WebUtils {

    fun openWebView(activity: Activity, url: String) {
        Log.e("pLog", "openWebView ==== $url")
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        intent.putExtra("jump", true)
        activity.startActivityForResult(intent, 1002)
    }

    fun closeWebView(activity: Activity) {
        Log.e("pLog", "closeWebView --=====")
        activity.finish()
    }


    fun openAndroid(activity: Activity, url: String) {
        Log.e("pLog", "openAndroid --=====$url")
        try {
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                val chooser = Intent.createChooser(this, "Choose a browser")
                activity.startActivity(chooser)
            }
        } catch (e: Exception) {
            Log.e("xxxTag", "openSystemBrowser failure $e")
        }
    }


    fun eventTracker(activity: Activity, eventType: String, eventValues: String) {
        Log.e("pLog", "eventTracker --=====$eventType ===  eventValues = $eventValues")
        if (eventValues.isNotEmpty()) {
            val gson = Gson()
            val type = object : TypeToken<Map<String?, String>>() {}.type
            val map = gson.fromJson<Map<String, String>>(eventValues, type)
            AppsFlyerLib.getInstance().logEvent(activity, eventType, map)
        }
    }

    fun logEvent(activity: Activity, tag: String, value: String) {
        val gson = Gson()
        val type = object : TypeToken<Map<String?, Any?>?>() {}.type
        var eventValues: MutableMap<String?, Any?>

        eventValues = HashMap()
            eventValues[AFInAppEventParameterName.CONTENT_ID] = tag
            eventValues[AFInAppEventParameterName.CONTENT_TYPE] = tag
            eventValues[AFInAppEventParameterName.CONTENT] = value
            try {
                val jsonObject = JSONObject(value)
                val amount = jsonObject.optString("amount")
                eventValues[AFInAppEventParameterName.REVENUE] = amount
                eventValues[AFInAppEventParameterName.CURRENCY] = "PHP"
                Log.e("pLog", "logEvent: amount = $amount")
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            } 
        AppsFlyerLib.getInstance()
            .logEvent(activity, tag, eventValues, object : AppsFlyerRequestListener {
                override fun onSuccess() {
                    Log.i("pLog", "AppsFlyerLib onSuccess")
                }

                override fun onError(i: Int, s: String) {
                    Log.i("pLog", "AppsFlyerLib onError")
                }
            })
        
    }

}