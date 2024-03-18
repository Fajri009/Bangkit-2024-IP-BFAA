package com.example.bangkit_2024_ip_bfaa.data.retrofit

import com.example.bangkit_2024_ip_bfaa.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") user: String
    ): Call<GithubResponse>
}