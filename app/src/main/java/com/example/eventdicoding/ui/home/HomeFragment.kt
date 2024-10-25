package com.example.eventdicoding.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.databinding.FragmentHomeBinding
import com.example.eventdicoding.ui.DetailActivity
import com.example.eventdicoding.ui.EventAdapter
import com.example.eventdicoding.data.Result
import com.example.eventdicoding.ui.favorite.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventAdapterUpcoming: EventAdapter
    private lateinit var eventAdapterFinished: EventAdapter
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeViewModel()
        return binding.root
    }

    private fun setupRecyclerView() {
        eventAdapterUpcoming = EventAdapter { selectedEvent ->
            navigateToDetail(selectedEvent.id)
        }

        eventAdapterFinished = EventAdapter { selectedEvent ->
            navigateToDetail(selectedEvent.id)
        }

        binding.rvUpcomingEvents.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = eventAdapterUpcoming
        }

        binding.rvFinishedEvents.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = eventAdapterFinished
        }
    }

    private fun observeViewModel() {
        homeViewModel.eventUpcoming.observe(viewLifecycleOwner) { eventList ->
            when (eventList) {
                is Result.Success -> {
                    eventAdapterUpcoming.submitList(eventList.data.toMutableList())
                    showLoading(false)
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(context, "Error fetching upcoming events", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    showLoading(true)
                }
            }
        }

        homeViewModel.eventFinished.observe(viewLifecycleOwner) { eventList ->
            when (eventList) {
                is Result.Success -> {
                    eventAdapterFinished.submitList(eventList.data.toMutableList())
                    showLoading(false)
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(context, "Error fetching upcoming events", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    showLoading(true)
                }
            }
        }
    }

    private fun navigateToDetail(eventId: String) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("EVENT_ID", eventId)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}