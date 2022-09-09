package com.skillbox.github.data

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface GithubApi {
    @GET("user")
    fun infoUser() : Call<UserInfo.CurrentUser>

    @GET("user/repos")
    fun reposUser() : Call<List<UserInfo.ReposUser>>

    @GET("user/starred/{owner}/{repo}")
    fun infoStarred(
        @Path("owner") ownerName: String,
        @Path("repo") repository: String
    ) : Call<Boolean>

    @PUT("user/starred/{owner}/{repo}")
    fun putStarred(
        @Path("owner") ownerName: String,
        @Path("repo") repository: String
    ) : Call<Boolean>

    @DELETE("user/starred/{owner}/{repo}")
    fun delStarred(
        @Path("owner") ownerName: String,
        @Path("repo") repository: String
    ) : Call<Boolean>
}