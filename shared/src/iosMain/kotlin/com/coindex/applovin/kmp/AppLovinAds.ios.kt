package com.coindex.applovin.kmp

import cocoapods.AppLovinSDK.ALMediationProviderMAX
import cocoapods.AppLovinSDK.ALSdk
import cocoapods.AppLovinSDK.ALSdkInitializationConfiguration
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
actual object AppLovinAds {

    actual fun initialize(context: Any?, sdkKey: String) {
        val initConfig =
            ALSdkInitializationConfiguration.configurationWithSdkKey(sdkKey = sdkKey) { builderBlock ->
                builderBlock?.mediationProvider = ALMediationProviderMAX
            }
        ALSdk.shared().initializeWithConfiguration(initConfig) { sdkConfig ->

        }
    }
}