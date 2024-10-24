package com.example.eventdicoding.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.eventdicoding.data.Result
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.data.local.entity.EventEntity
import com.google.android.material.snackbar.Snackbar
import com.example.eventdicoding.databinding.FragmentFavoriteBinding
import com.example.eventdicoding.ui.DetailActivity
import com.example.eventdicoding.ui.FavoriteAdapter
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private  var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteAdapter


    private val viewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeFavoriteEvents()
    }

    private fun setupRecyclerView() {
        adapter = FavoriteAdapter{event ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("EVENT_ID", event.id)
            }
            startActivity(intent)
        }
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoriteFragment.adapter
        }
    }

    private fun observeFavoriteEvents() {
        viewModel.favoriteEvents.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.submitList(result.data)
                    binding.tvNoEventFavorite.visibility = if (result.data.isEmpty()){
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }

            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvFavorite.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}