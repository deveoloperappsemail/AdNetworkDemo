package com.example.adnetworkdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import com.applovin.adview.AppLovinInterstitialAd
import com.applovin.sdk.AppLovinSdk
import com.example.adnetworkdemo.applovin.AppLovinAds

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Testing

        val adFrame = findViewById<FrameLayout>(R.id.native_ad_layout)
        AppLovinAds.loadNativeAd(this, adFrame)

        AppLovinAds.loadInterstitialAd(this, this)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            AppLovinAds.showAd(this, Intent(this, NextActivity::class.java))
        }

    }
}