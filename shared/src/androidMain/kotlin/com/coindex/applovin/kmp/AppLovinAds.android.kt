package com.coindex.applovin.kmp

import android.content.Context
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkInitializationConfiguration

actual object AppLovinAds {

    actual fun initialize(context: Any?, sdkKey: String) {
        // Create the initialization configuration
        val initConfig = AppLovinSdkInitializationConfiguration.builder(sdkKey)
            .setMediationProvider(AppLovinMediationProvider.MAX)
            .build()
        // Initialize the SDK with the configuration
        AppLovinSdk.getInstance(context as Context).initialize(initConfig) { sdkConfig ->
            // Start loading ads
        }
    }
}