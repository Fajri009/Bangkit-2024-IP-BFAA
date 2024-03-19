package com.example.bangkit_2024_ip_bfaa.ui.detailuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkit_2024_ip_bfaa.data.response.UserResponse
import com.example.bangkit_2024_ip_bfaa.databinding.FragmentFollowerBinding

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowerBinding
    private var position: Int = 0
    private var username: String? = ""
    private lateinit var viewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowViewModel::class.java)

        showRecyclerList()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
            username = it.getString(ARG_CURR_USERNAME)
        }

        if (position == 1) {
//            binding.testUsername.text = "Get Follower $username"
            viewModel.listFollowers.observe(viewLifecycleOwner) {
                setFollowerData(it)
            }

            viewModel.getFollowers(username!!)
        } else {
//            binding.testUsername.text = "Get Following $username"
            viewModel.listFollowing.observe(viewLifecycleOwner) {
                setFollowerData(it)
            }

            viewModel.getFollowing(username!!)
        }
    }

    private fun showRecyclerList() {
        val layoutInflater = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutInflater
    }

    private fun setFollowerData(user: List<UserResponse>) {
        val adapter = FollowAdapter()
        adapter.submitList(user)
        binding.recyclerView.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_CURR_USERNAME = "curr_username"
    }
}