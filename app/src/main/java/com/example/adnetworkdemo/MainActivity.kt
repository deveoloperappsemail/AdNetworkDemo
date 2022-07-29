package com.example.adnetworkdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.allnetworkadslibrary.Ads
import com.example.allnetworkadslibrary.adslib.Constants
import com.example.allnetworkadslibrary.adslib.LiveAds
import com.example.allnetworkadslibrary.adslib.SharedPrefUtils
import com.example.allnetworkadslibrary.adslib.TestAds

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val adFrame = findViewById<FrameLayout>(R.id.native_ad_layout)

        /*to show admob ads save true
        to show applovin ads save false*/
        val showAdmob = false
        TestAds.getTestAds(this, showAdmob, packageName)

        //LiveAds.getLiveAds(this, packageName)

        //pass applovin ad id
        Ads.loadNative(this, this, adFrame)
        Ads.loadInter(this, this)

        button.setOnClickListener {
            val intent = Intent(this, NextActivity::class.java)
            Ads.showInter(this, this, intent)
        }
    }

    private fun oldCode() {
        //Admob ads testing
        /*TestAds.getTestAds(this)
        AdmobAds.refreshAd(this, this, adFrame)
        AdmobAds.loadAdmobInters(this)
        button.setOnClickListener {
            AdmobAds.showInterAd(this, Intent(this, NextActivity::class.java))
        }*/

        //Applovin ads testing
        /*AppLovinAds.loadNativeAd(getString(R.string.native_test_id),this, adFrame)

        AppLovinAds.loadInterstitialAd(getString(R.string.inter_test_id), this, this)

        button.setOnClickListener {
            AppLovinAds.showAd(this, Intent(this, NextActivity::class.java))
        }*/
    }
}