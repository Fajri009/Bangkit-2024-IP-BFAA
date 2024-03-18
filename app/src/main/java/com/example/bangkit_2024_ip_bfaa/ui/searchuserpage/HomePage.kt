package com.example.bangkit_2024_ip_bfaa.ui.searchuserpage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkit_2024_ip_bfaa.data.response.UserResponse
import com.example.bangkit_2024_ip_bfaa.databinding.ActivityHomeBinding

class HomePage : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutInflater = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutInflater

        homeViewModel.listUser.observe(this) {
            setUserData(it)
        }

        homeViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        search(binding)
    }

    private fun search(binding: ActivityHomeBinding) {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    homeViewModel.findUsers(searchView.text.toString())
                    false
                }
        }
    }

    private fun setUserData(user: List<UserResponse>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}