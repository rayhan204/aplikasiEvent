package com.example.eventdicoding.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.databinding.FragmentUpcomingBinding
import com.example.eventdicoding.ui.DetailActivity
import com.example.eventdicoding.ui.EventAdapter

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventAdapter: EventAdapter
    private lateinit var upcomingViewModel: UpcomingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        upcomingViewModel = ViewModelProvider(this)[UpcomingViewModel::class.java]

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        eventAdapter = EventAdapter{ selectedEvent ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", selectedEvent.id)
            startActivity(intent)
        }
        binding.rvEvents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventAdapter
        }

        upcomingViewModel.events.observe(viewLifecycleOwner) { eventList ->
            eventAdapter.submitList(eventList)
            binding.progressBar.visibility = View.GONE
        }

        upcomingViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}