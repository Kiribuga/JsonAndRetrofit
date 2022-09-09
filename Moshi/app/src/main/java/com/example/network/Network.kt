package com.example.network

import com.example.network.data.API_KEY
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object Network {
    val flipperNetworkPlugin = NetworkFlipperPlugin()

    private val client = OkHttpClient.Builder()
        .addInterceptor(CustomInterceptor("apikey", API_KEY))
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .addNetworkInterceptor(FlipperOkhttpInterceptor(flipperNetworkPlugin))
        .build()

    fun getSearchMovieCall(title: String): Call {
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("www.omdbapi.com")
            .addQueryParameter("t", title)
            .build()

        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        return client.newCall(request)
    }
}