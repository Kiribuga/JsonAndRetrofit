package com.example.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.network.data.RemoteMovie
import okhttp3.Call

class ViewModelMovie : ViewModel() {

    private var currentCall: Call? = null

    private val repository = RepositoryMovie()

    private val movieListLiveData = MutableLiveData<List<RemoteMovie>>()

    private val isLoadingLiveData = MutableLiveData<Boolean>()

    private val errorDownloadLiveData = MutableLiveData<Boolean>()

    private val errorMessageLiveData = MutableLiveData<String>()

//    private val newScoreLiveData = MutableLiveData<Map<String, String>>()
//
//    val newScore: LiveData<Map<String, String>>
//        get() = newScoreLiveData

    val movieList: LiveData<List<RemoteMovie>>
        get() = movieListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val errorDownload: LiveData<Boolean>
        get() = errorDownloadLiveData

    val errorMessage: LiveData<String>
        get() = errorMessageLiveData

    fun search(title: String) {
        isLoadingLiveData.postValue(true)
        currentCall = repository.searchMovie(title, { movies ->
            isLoadingLiveData.postValue(false)
            movieListLiveData.postValue(movies)
            currentCall = null
        }) { error ->
            if (error == Throwable(message = null)) {
                errorDownloadLiveData.postValue(false)
            } else {
                errorDownloadLiveData.postValue(true)
                errorMessageLiveData.postValue("${error.message}")
            }
        }
    }

    fun serializeMovie(score: MutableMap<String, String>) {
        val movieWithScore = movieListLiveData.value?.first()?.run {
            RemoteMovie(title, year, poster, genre, rating, scores.plus(score).toMutableMap())
        }
        repository.parseToJsonMovie(movieWithScore)
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}
