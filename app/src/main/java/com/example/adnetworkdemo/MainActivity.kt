package com.example.adnetworkdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import com.example.allnetworkadslibrary.admob.AdmobAds
import com.example.allnetworkadslibrary.adslib.TestAds
import com.example.allnetworkadslibrary.applovin.AppLovinAds

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val adFrame = findViewById<FrameLayout>(R.id.native_ad_layout)

        //Admob ads testing
      /*  TestAds.getTestAds(this)
        AdmobAds.refreshAd(this, this, adFrame)
        AdmobAds.loadAdmobInters(this)
        button.setOnClickListener {
            AdmobAds.showInterAd(this, Intent(this, NextActivity::class.java))
        }*/

        //Applovin ads testing
        AppLovinAds.loadNativeAd(getString(R.string.native_test_id),this, adFrame)

        AppLovinAds.loadInterstitialAd(getString(R.string.inter_test_id), this, this)

        button.setOnClickListener {
            AppLovinAds.showAd(this, Intent(this, NextActivity::class.java))
        }

    }
}