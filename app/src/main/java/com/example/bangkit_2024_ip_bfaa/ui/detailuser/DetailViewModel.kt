package com.example.bangkit_2024_ip_bfaa.ui.detailuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bangkit_2024_ip_bfaa.data.response.DetailUserResponse
import com.example.bangkit_2024_ip_bfaa.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _listDetail = MutableLiveData<DetailUserResponse>()
    val listDetail: LiveData<DetailUserResponse> = _listDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getUser(user: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(user)
        client.enqueue(object: Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _listDetail.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailter: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}