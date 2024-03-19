package com.example.bangkit_2024_ip_bfaa.ui.detailuser

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.bangkit_2024_ip_bfaa.R
import com.example.bangkit_2024_ip_bfaa.data.response.DetailUserResponse
import com.example.bangkit_2024_ip_bfaa.data.response.UserResponse
import com.example.bangkit_2024_ip_bfaa.databinding.ActivityDetailPageBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailPage : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPageBinding
    private val viewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_title_1,
            R.string.tab_title_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<UserResponse>(EXTRA_USER)

        back()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        var current_user = user?.login.toString()

        viewModel.getUser(current_user)

        viewModel.listDetail.observe(this) {
            setShowUser(it)
        }

        setViewPager(current_user)
    }

    private fun back() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setShowUser(listUser: DetailUserResponse) {
        Glide.with(this@DetailPage)
            .load(listUser.avatarUrl)
            .circleCrop()
            .into(binding.ivAvatar)
        binding.tvNama.text = listUser.name
        binding.tvUsername.text = listUser.login
        binding.tvTwitter.text =
            if (listUser.twitterUsername.toString() == "null") {
                "-"
            } else {
                listUser.twitterUsername.toString()
            }
        binding.tvLocation.text = listUser.location
        binding.tvFollower.text = "${listUser.followers} followers"
        binding.tvFollowing.text = "${listUser.following} following"
    }

    private fun setViewPager(curr_user: String) {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = curr_user
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, positon ->
            tab.text = resources.getString(TAB_TITLES[positon])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
