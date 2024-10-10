package com.example.eventdicoding.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.eventdicoding.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityDetailBinding

    private  lateinit var detailActivityViewModel: DetailActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailActivityViewModel = ViewModelProvider(this)[DetailActivityViewModel::class.java]

        val eventId = intent.getIntExtra("EVENT_ID",0)

        detailActivityViewModel.loadEventDetail(eventId)

        detailActivityViewModel.event.observe(this) {event ->
            binding.tvName.text = event.name
            binding.tvOwner.text = event.ownerName
            binding.tvBeginTime.text = event.beginTime
            binding.tvQuota.text = event.quota.toString()
            binding.tvDescription.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)


            binding.btnLink.setOnClickListener{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                startActivity(intent)
            }
            Glide.with(this)
                .load(event.mediaCover)
                .centerCrop()
                .into(binding.ivCover)
        }

        detailActivityViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}