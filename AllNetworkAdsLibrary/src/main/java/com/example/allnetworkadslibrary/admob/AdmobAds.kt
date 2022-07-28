package com.example.allnetworkadslibrary.admob

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.*
import com.example.allnetworkadslibrary.R;
import com.example.allnetworkadslibrary.adslib.Constants
import com.example.allnetworkadslibrary.adslib.SharedPrefUtils
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

class AdmobAds {

    companion object {

        var mInterstitialAd: InterstitialAd? = null
        private var nativeAd1: NativeAd? = null

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

        private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
            // Set the media view.
            adView.mediaView = adView.findViewById<View>(R.id.ad_media) as MediaView

            // Set other ad assets.
            adView.headlineView = adView.findViewById(R.id.ad_headline)
            adView.bodyView = adView.findViewById(R.id.ad_body)
            adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
            adView.iconView = adView.findViewById(R.id.ad_app_icon)
            adView.priceView = adView.findViewById(R.id.ad_price)
            adView.starRatingView = adView.findViewById(R.id.ad_stars)
            adView.storeView = adView.findViewById(R.id.ad_store)
            adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

            // The headline and mediaContent are guaranteed to be in every NativeAd.
            (adView.headlineView as TextView).text = nativeAd.headline
            adView.mediaView!!.setMediaContent(nativeAd.mediaContent!!)

            // These assets aren't guaranteed to be in every NativeAd, so it's important to
            // check before trying to display them.
            if (nativeAd.body == null) {
                adView.bodyView!!.visibility = View.INVISIBLE
            } else {
                adView.bodyView!!.visibility = View.VISIBLE
                (adView.bodyView as TextView).text = nativeAd.body
            }
            if (nativeAd.callToAction == null) {
                adView.callToActionView!!.visibility = View.INVISIBLE
            } else {
                adView.callToActionView!!.visibility = View.VISIBLE
                (adView.callToActionView as Button).text = nativeAd.callToAction
            }
            if (nativeAd.icon == null) {
                adView.iconView!!.visibility = View.GONE
            } else {
                (adView.iconView as ImageView).setImageDrawable(
                    nativeAd.icon!!.drawable
                )
                adView.iconView!!.visibility = View.VISIBLE
            }
            if (nativeAd.price == null) {
                adView.priceView!!.visibility = View.INVISIBLE
            } else {
                adView.priceView!!.visibility = View.VISIBLE
                (adView.priceView as TextView).text = nativeAd.price
            }
            if (nativeAd.store == null) {
                adView.storeView!!.visibility = View.INVISIBLE
            } else {
                adView.storeView!!.visibility = View.VISIBLE
                (adView.storeView as TextView).text = nativeAd.store
            }
            if (nativeAd.starRating == null) {
                adView.starRatingView!!.visibility = View.INVISIBLE
            } else {
                (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
                adView.starRatingView!!.visibility = View.VISIBLE
            }
            if (nativeAd.advertiser == null) {
                adView.advertiserView!!.visibility = View.INVISIBLE
            } else {
                (adView.advertiserView as TextView).text = nativeAd.advertiser
                adView.advertiserView!!.visibility = View.VISIBLE
            }

            // This method tells the Google Mobile Ads SDK that you have finished populating your
            // native ad view with this native ad.
            adView.setNativeAd(nativeAd)

            // Get the video controller for the ad. One will always be provided, even if the ad doesn't
            // have a video asset.
            val vc = nativeAd.mediaContent!!.videoController

            // Updates the UI to say whether or not this ad has a video asset.
            if (vc.hasVideoContent()) {


                // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
                // VideoController will call methods on this object when events occur in the video
                // lifecycle.
                vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
                    override fun onVideoEnd() {
                        // Publishers should allow native ads to complete video playback before
                        // refreshing or replacing them with another ad in the same UI location.
                        super.onVideoEnd()
                    }
                }
            }
        }

        /**
         * Creates a request for a new native ad based on the boolean parameters and calls the
         * corresponding "populate" method when one is successfully returned.
         */
        @SuppressLint("InflateParams")
        fun refreshAd(
            context: Context,
            activity: Activity,
            frameLayout: FrameLayout
        ) {
            var nativeAdId = SharedPrefUtils.getStringData(context, Constants.NATIVE_AD)
            if (nativeAdId == null)
                nativeAdId = "no"

            val builder = AdLoader.Builder(context, nativeAdId)
            builder.forNativeAd(
                NativeAd.OnNativeAdLoadedListener { nativeAd ->

                    // OnLoadedListener implementation.
                    // If this callback occurs after the activity is destroyed, you must call
                    // destroy and return or you may get a memory leak.
                    try {
                        var isDestroyed = false
                        isDestroyed = activity.isDestroyed
                        if (isDestroyed || activity.isFinishing || activity.isChangingConfigurations) {
                            nativeAd1!!.destroy()
                            return@OnNativeAdLoadedListener
                        }
                        // You must call destroy on old ads when you are done with them,
                        // otherwise you will have a memory leak.
                        if (nativeAd1 != null) {
                            nativeAd1!!.destroy()
                        }
                        nativeAd1 = nativeAd
                        val adView = activity.layoutInflater.inflate(
                            R.layout.ad_unified,
                            null
                        ) as NativeAdView
                        populateNativeAdView(nativeAd, adView)
                        frameLayout.removeAllViews()
                        frameLayout.addView(adView)

                        frameLayout.visibility = View.VISIBLE
                        //advertisementArea.visibility = View.GONE
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                })
            val videoOptions = VideoOptions.Builder().setStartMuted(false).build()
            val adOptions = NativeAdOptions.Builder().setVideoOptions(videoOptions).build()
            builder.withNativeAdOptions(adOptions)
            val adLoader = builder
                .withAdListener(
                    object : AdListener() {
                        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                            frameLayout.visibility = View.GONE
                           // advertisementArea.visibility = View.VISIBLE
                        }
                    })
                .build()
            adLoader.loadAd(AdRequest.Builder().build())
        }
    }
}
