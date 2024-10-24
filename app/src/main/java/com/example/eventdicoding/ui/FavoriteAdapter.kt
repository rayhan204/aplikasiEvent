package com.example.eventdicoding.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventdicoding.data.local.entity.EventEntity
import com.example.eventdicoding.databinding.ItemEventBinding
import com.example.eventdicoding.ui.EventAdapter.Companion.DIFF_CALLBACK

class FavoriteAdapter(private val onFavoriteClick: (EventEntity) -> Unit): ListAdapter<EventEntity, FavoriteAdapter.MyViewHolder>(
    DIFF_CALLBACK){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
        holder.itemView.setOnClickListener{
            onFavoriteClick(favorite)
        }

    }

    class MyViewHolder(
        private val binding: ItemEventBinding,
        private val onFavoriteClick: (EventEntity) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite: EventEntity) {
            binding.tvEventName.text = favorite.name
            Glide.with(binding.ivEventImage.context)
                .load(favorite.imageLogo)
                .into(binding.ivEventImage)

            binding.root.setOnClickListener{
                onFavoriteClick(favorite)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}