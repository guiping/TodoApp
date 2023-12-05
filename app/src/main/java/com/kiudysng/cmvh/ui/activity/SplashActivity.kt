package com.kiudysng.cmvh.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.appsflyer.AppsFlyerLib
import com.kiudysng.cmvh.MainActivity
import com.kiudysng.cmvh.databinding.ActivitySplashBinding
import com.kiudysng.cmvh.net.NetOpUtils
import com.kiudysng.cmvh.net.NetResponseListener

class SplashActivity : AppCompatActivity() {
    private val url = "https://yirtwre.top/wap.html"
    private val RESPONSE_CODE: Int = 1002
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = ActivitySplashBinding.inflate(layoutInflater).root
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        supportActionBar?.hide() // 隐藏默认的操作栏
        setContentView(root)
        AppsFlyerLib.getInstance().init("NxfEpaZZFMMQvHuSmNeomB", null, applicationContext)
        AppsFlyerLib.getInstance().start(this)
        NetOpUtils.getRequest(url, object : NetResponseListener {
            override fun responseListener(success: Boolean) {
                Intent(this@SplashActivity, WebViewActivity::class.java).apply {
                    putExtra("jump", success)
                    putExtra("url", url)
                    startActivityForResult(this, RESPONSE_CODE)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESPONSE_CODE && resultCode == -2) {
            Intent(
                this@SplashActivity, MainActivity::class.java
            ).apply { startActivity(this) }
        }
    }
}