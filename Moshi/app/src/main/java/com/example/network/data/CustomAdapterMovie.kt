package com.example.network.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

class CustomAdapterMovie {

    @FromJson
    fun fromJson(movie: MovieWrapper): RemoteMovie {
        return RemoteMovie(
            title = movie.title,
            year = movie.year,
            poster = movie.poster,
            genre = movie.genre,
            rating = movie.rating,
            scores = movie.scores.associateBy({ it.source }, { it.value }).toMutableMap()
        )
    }

    @ToJson
    fun toJson(movie: RemoteMovie): MovieWrapper {
        var i = 0
        var resultList = emptyList<Score>()
        var listScore = emptyList<Pair<String, String>>()
        val stringList = movie.scores.toList()
        val stringSize = stringList.size - 1
        while (stringSize >= i) {
            listScore = listScore + stringList[i]
            resultList = resultList + listScore.run {
                List<Score>(1) { Score(source = listScore[i].first, value = listScore[i].second) }
            }
            i++
        }

        return MovieWrapper(
            title = movie.title,
            year = movie.year,
            poster = movie.poster,
            genre = movie.genre,
            rating = movie.rating,
            scores = resultList
        )
    }

    @JsonClass(generateAdapter = true)
    data class MovieWrapper(
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
        val scores: List<Score>
    )
}