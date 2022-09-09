package com.example.network

import android.util.Log
import com.example.network.data.CustomAdapterMovie
import com.example.network.data.RemoteMovie
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.lang.Exception

class RepositoryMovie {

    fun searchMovie(
        title: String,
        callback: (List<RemoteMovie>) -> Unit,
        errorCallback: (e: Throwable) -> Unit
    ): Call {

        return Network.getSearchMovieCall(title).apply {
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: java.io.IOException) {
                    Log.d("Server", "execute request error = ${e.message}", e)
                    errorCallback(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseString = response.body?.string()
                        val movies = parseMovieResponse(responseString!!)
                        callback(movies)
                    } else {
                        errorCallback(Throwable("Error response"))
                    }
                }
            })
        }
    }

    private fun parseMovieResponse(responseString: String): List<RemoteMovie> {
        val moshi = Moshi.Builder()
            .add(CustomAdapterMovie())
            .build()
        val adapter = moshi.adapter(RemoteMovie::class.java).nonNull()
        try {
            val movie = adapter.fromJson(responseString)
            val title = movie?.title
            val year = movie?.year
            val poster = movie?.poster
            val genre = movie?.genre
            val rating = movie?.rating
            val scores = movie?.scores

            val movies = listOf(
                RemoteMovie(
                    title = title!!,
                    year = year!!,
                    poster = poster!!,
                    genre = genre!!,
                    rating = rating!!,
                    scores = scores!!
                )
            )
            return movies
        } catch (e: Exception) {
            Log.d("Server", "parse error = ${e.message}", e)
            return emptyList()
        }
    }

    fun parseToJsonMovie(movie: RemoteMovie?) {
        val moshi = Moshi.Builder()
            .add(CustomAdapterMovie())
            .build()
        val adapter = moshi.adapter(RemoteMovie::class.java).nonNull()
        try {
            val movieJson = adapter.toJson(movie)
            Log.d("Serializable", "result serializable = $movieJson")
        } catch (e: Exception) {
            Log.d("Server", "parse error serializable = ${e.message}", e)
        }
    }
}