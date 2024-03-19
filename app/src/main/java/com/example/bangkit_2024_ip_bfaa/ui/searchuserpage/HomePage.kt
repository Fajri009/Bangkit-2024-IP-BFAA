package com.example.bangkit_2024_ip_bfaa.ui.searchuserpage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkit_2024_ip_bfaa.data.response.UserResponse
import com.example.bangkit_2024_ip_bfaa.databinding.ActivityHomePageBinding
import com.example.bangkit_2024_ip_bfaa.ui.detailuser.DetailPage

class HomePage : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecyclerList()

        viewModel.listUser.observe(this) {
            setUserData(it)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        search(binding)
    }

    private fun showRecyclerList() {
        val layoutInflater = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutInflater
    }

    private fun search(binding: ActivityHomePageBinding) {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    viewModel.findUsers(searchView.text.toString())
                    false
                }
        }
    }

    private fun setUserData(user: List<UserResponse>) {
        val adapter = UserAdapter(user)
        adapter.submitList(user)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: UserResponse) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(data: UserResponse) {
        val moveWithParcelableIntent = Intent(this@HomePage, DetailPage::class.java)
        moveWithParcelableIntent.putExtra(DetailPage.EXTRA_USER, data)
        startActivity(moveWithParcelableIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}