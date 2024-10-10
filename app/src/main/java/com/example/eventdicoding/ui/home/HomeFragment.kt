package com.example.eventdicoding.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.databinding.FragmentHomeBinding
import com.example.eventdicoding.ui.EventAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventAdapterUpcoming: EventAdapter
    private lateinit var eventAdapterFinished: EventAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        eventAdapterUpcoming = EventAdapter { selectedEvent ->
        }

        eventAdapterFinished = EventAdapter { selectedEvent ->
        }

        binding.rvUpcomingEvents.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = eventAdapterUpcoming
        }

        binding.rvFinishedEvents.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = eventAdapterFinished
        }

        homeViewModel.upcomingEvents.observe(viewLifecycleOwner) { eventList ->
            eventAdapterUpcoming.submitList(eventList.take(5)) // Display max 5 events
        }

        homeViewModel.finishedEvents.observe(viewLifecycleOwner) { eventList ->
            eventAdapterFinished.submitList(eventList.take(5)) // Display max 5 events
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}