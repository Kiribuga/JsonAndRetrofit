package com.example.network.utils

import android.app.Application
import com.example.network.BuildConfig
import com.example.network.Network
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            AndroidFlipperClient.getInstance(this)
                .apply {
                    addPlugin(InspectorFlipperPlugin(this@App, DescriptorMapping.withDefaults()))
                    addPlugin(Network.flipperNetworkPlugin)
                }
                .start()
        }
    }
}