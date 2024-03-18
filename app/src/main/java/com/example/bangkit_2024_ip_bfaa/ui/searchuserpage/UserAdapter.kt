package com.example.bangkit_2024_ip_bfaa.ui.searchuserpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkit_2024_ip_bfaa.data.response.UserResponse
import com.example.bangkit_2024_ip_bfaa.databinding.ItemListUserBinding

class UserAdapter: ListAdapter<UserResponse, UserAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(val binding: ItemListUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: UserResponse) {
            Glide.with(itemView.context)
                .load(items.avatarUrl)
                .circleCrop()
                .into(binding.ivAvatar)
            binding.tvNama.text = items.login
        }
    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<UserResponse>() {
            override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }
}