package com.skillbox.github.ui.user_info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.data.UserInfo
import com.skillbox.github.data.UserRepository

class UserViewModel : ViewModel() {

    private val repository = UserRepository()
    private val userListLiveData = MutableLiveData<List<UserInfo.CurrentUser>>()
    private val reposListLiveData = MutableLiveData<List<UserInfo.ReposUser>>()
    private val infoStarredLiveData = MutableLiveData<Boolean>()
    private val putStarredLiveData = MutableLiveData<Boolean>()
    private val deleteStarredLiveData = MutableLiveData<Boolean>()

    val userList: LiveData<List<UserInfo.CurrentUser>>
        get() = userListLiveData

    val reposList: LiveData<List<UserInfo.ReposUser>>
        get() = reposListLiveData

    val infoStarred: LiveData<Boolean>
        get() = infoStarredLiveData

    val putStarred: LiveData<Boolean>
        get() = putStarredLiveData

    val delStarred: LiveData<Boolean>
        get() = deleteStarredLiveData

    fun getUser() {
        repository.infoUser({ user ->
            userListLiveData.postValue(user)
        }) {
            userListLiveData.postValue(emptyList())
        }
    }

    fun getRepos() {
        repository.reposInfo({ repos ->
            reposListLiveData.postValue(repos)
        }) {
            reposListLiveData.postValue(emptyList())
        }
    }

    fun infoStarredFun(nameUser: String, nameRepo: String) {
        repository.infoStarred(nameUser, nameRepo, { star ->
            infoStarredLiveData.postValue(star)
        }) { error ->
            Log.d("ViewModel", "Error info = $error")
        }
    }

    fun putStar(nameUser: String, nameRepo: String) {
        repository.putStar(nameUser, nameRepo, { result ->
            putStarredLiveData.postValue(result)
        }) { error ->
            Log.d("ViewModel", "Error put a star = $error")
        }
    }

    fun deleteStar(nameUser: String, nameRepo: String) {
        repository.delStar(nameUser, nameRepo, { result ->
            deleteStarredLiveData.postValue(result)
        }) {error ->
            Log.d("ViewModel", "Error delete a star = $error")
        }
    }
}