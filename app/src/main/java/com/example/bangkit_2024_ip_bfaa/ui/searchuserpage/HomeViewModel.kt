package com.example.bangkit_2024_ip_bfaa.ui.searchuserpage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bangkit_2024_ip_bfaa.data.response.GithubResponse
import com.example.bangkit_2024_ip_bfaa.data.response.UserResponse
import com.example.bangkit_2024_ip_bfaa.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel(){
    companion object {
        private const val TAG = "HomeViewModel"
        private const val USER_ID = "Arif"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<List<UserResponse>>()
    val listUser: LiveData<List<UserResponse>> = _listUser

    init {
        findUsers(USER_ID)
    }

    fun findUsers(query: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUsers(query)
        client.enqueue(object: Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _listUser.value = response.body()?.items
                    }
                    if (response.body()?.items!!.isNotEmpty()){
                        Log.d("Nope", "Gak ada")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}