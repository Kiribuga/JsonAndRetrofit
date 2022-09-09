package com.skillbox.github.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class UserRepository {

    fun infoUser(
        onComplete: (List<UserInfo.CurrentUser>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Network.githubApi.infoUser().enqueue(
            object : Callback<UserInfo.CurrentUser> {
                override fun onFailure(call: Call<UserInfo.CurrentUser>, t: Throwable) {
                    onError(t)
                }
                override fun onResponse(
                    call: Call<UserInfo.CurrentUser>,
                    response: Response<UserInfo.CurrentUser>
                ) {
                    if (response.isSuccessful) {
                        val complete = listOf(response.body())
                        onComplete(complete as List<UserInfo.CurrentUser>)
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }
            }
        )
    }

    fun reposInfo(
        onComplete: (List<UserInfo.ReposUser>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Network.githubApi.reposUser().enqueue(
            object : Callback<List<UserInfo.ReposUser>> {
                override fun onFailure(call: Call<List<UserInfo.ReposUser>>, t: Throwable) {
                    onError(t)
                }
                override fun onResponse(
                    call: Call<List<UserInfo.ReposUser>>,
                    response: Response<List<UserInfo.ReposUser>>
                ) {
                    if (response.isSuccessful) {
                        onComplete(response.body().orEmpty())
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }
            }
        )
    }

    fun infoStarred(
        nameUser: String,
        nameRepo: String,
        onComplete: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ){
        Network.githubApi.infoStarred(nameUser, nameRepo).enqueue(
            object : Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onError(t)
                }
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    when {
                        response.code() == 204 -> {
                            onComplete(true)
                        }
                        response.code() == 404 -> {
                            onComplete(false)
                        }
                        else -> {
                            onError(RuntimeException("incorrect status code"))
                        }
                    }
                }
            }
        )
    }

    fun putStar(
        nameUser: String,
        nameRepo: String,
        onComplete: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Network.githubApi.putStarred(nameUser, nameRepo).enqueue(
            object : Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onError(t)
                }
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    when {
                        response.code() == 204 -> {
                            onComplete(true)
                        }
                        response.code() == 304 -> {
                            onComplete(false)
                        }
                        else -> {
                            onError(RuntimeException("incorrect status code"))
                        }
                    }
                }
            }
        )
    }

    fun delStar(
        nameUser: String,
        nameRepo: String,
        onComplete: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Network.githubApi.delStarred(nameUser, nameRepo).enqueue(
            object : Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    onError(t)
                }
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    when {
                        response.code() == 204 -> {
                            onComplete(true)
                        }
                        response.code() == 304 -> {
                            onComplete(false)
                        }
                        else -> {
                            onError(RuntimeException("incorrect status code"))
                        }
                    }
                }
            }
        )
    }
}