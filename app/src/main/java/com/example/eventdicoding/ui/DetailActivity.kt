package com.example.eventdicoding.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.eventdicoding.R
import com.example.eventdicoding.data.Result
import com.example.eventdicoding.data.local.entity.EventEntity
import com.example.eventdicoding.databinding.ActivityDetailBinding
import com.example.eventdicoding.ui.favorite.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailActivityViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private var isFavorite: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val eventId = intent.getStringExtra("EVENT_ID")
        viewModel.getDetailId(eventId?.toInt() ?: 0)

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.event.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    noDetail(false)
                    eventDetail(result.data)
                }
                is Result.Error -> {
                    showLoading(false)
                    noDetail(true)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.isFavorite.observe(this) { favoriteStatus ->
            isFavorite = favoriteStatus
            updateFavoriteIcon(isFavorite)
        }
    }

    private fun eventDetail(event: EventEntity) {
        binding.tvName.text = event.name
        binding.tvOwner.text = event.ownerName
        binding.tvBeginTime.text = event.beginTime
        binding.tvQuota.text = event.quota.toString()
        binding.tvDescription.text = event.description?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }

        val sisaQuota = event.quota?.minus(event.registrants!!)
        binding.tvQuota.text = getString(R.string.remaining_quota, sisaQuota)

        binding.btnLink.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
            startActivity(intent)
        }
        Glide.with(this)
            .load(event.mediaCover)
            .centerCrop()
            .into(binding.ivCover)

        binding.fabFavorite.setOnClickListener{
            viewModel.buttonFavorite(event, this@DetailActivity)
        }

    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        val iconResId = if (isFavorite) {
            R.drawable.baseline_favorite_24
        } else {
            R.drawable.baseline_favorite_border_24
        }
        binding.fabFavorite.setImageResource(iconResId)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun noDetail(noEvent: Boolean) {
        binding.tvNoDetailEvent.visibility = if (noEvent) View.VISIBLE else View.GONE
        binding.tvNoDetailEvent.gravity = Gravity.CENTER
        binding.cardView.visibility = if (noEvent) View.GONE else View.VISIBLE
    }

}