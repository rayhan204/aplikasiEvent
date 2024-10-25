package com.example.eventdicoding.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventdicoding.data.local.entity.EventEntity
import com.example.eventdicoding.databinding.ItemEventBinding

class EventAdapter(private val onItemClick: (EventEntity) -> Unit) : ListAdapter<EventEntity, EventAdapter.EventViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    override fun submitList(list: MutableList<EventEntity>?) {
        super.submitList(list)
    }

    class EventViewHolder(
        private val binding: ItemEventBinding,
        private val onItemClick: (EventEntity) -> Unit
        ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            binding.tvEventName.text = event.name
            Glide.with(binding.ivEventImage.context)
                .load(event.imageLogo)
                .into(binding.ivEventImage)

            binding.root.setOnClickListener{
                onItemClick(event)
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
