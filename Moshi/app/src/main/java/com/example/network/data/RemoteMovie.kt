package com.example.network.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovie(
    @Json(name = "Title")
    val title: String,
    @Json(name = "Year")
    val year: Int,
    @Json(name = "Poster")
    val poster: String,
    @Json(name = "Genre")
    val genre: String,
    @Json(name = "Rated")
    val rating: MovieRating = MovieRating.GENERAL,
    @Json(name = "Ratings")
    val scores: MutableMap<String, String>
)
