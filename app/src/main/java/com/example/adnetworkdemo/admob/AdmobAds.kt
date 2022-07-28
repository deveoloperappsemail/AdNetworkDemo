package com.example.adnetworkdemo.admob

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.adnetworkdemo.adslib.Constants
import com.example.adnetworkdemo.adslib.SharedPrefUtils
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdmobAds {

    companion object {

        var mInterstitialAd: InterstitialAd? = null

        fun loadAdmobInters(context: Context) {
            var interAdId = SharedPrefUtils.getStringData(context, Constants.INTERSTIAL)
            if (interAdId == null)
                interAdId = "no"

            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(context, interAdId, adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd
                        Log.i("TAG", "onAdLoaded")
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.message)
                        mInterstitialAd = null
                    }
                })
        }

        fun showInterAd(activity: Activity, intent: Intent) {
            if (mInterstitialAd != null) {
                mInterstitialAd!!.show(activity)
                mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        Log.d("TAG", "The ad was dismissed.")
                        // start login activity
                        activity.startActivity(intent)
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        // Called when fullscreen content failed to show.
                        Log.d("TAG", "The ad failed to show.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitialAd = null
                        Log.d("TAG", "The ad was shown.")
                    }
                }
            } else {
                activity.startActivity(intent)
            }
        }
    }
}
