package com.example.eventdicoding.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.data.Result
import com.example.eventdicoding.databinding.FragmentUpcomingBinding
import com.example.eventdicoding.ui.DetailActivity
import com.example.eventdicoding.ui.EventAdapter
import com.example.eventdicoding.ui.favorite.ViewModelFactory

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventAdapter: EventAdapter
    private val upcomingViewModel: UpcomingViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeViewModel()
        return binding.root
    }

    private fun setupRecyclerView() {
        eventAdapter = EventAdapter {  selectedEvent ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", selectedEvent.id)
            startActivity(intent)
        }

        binding.rvEvents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventAdapter
        }
    }

    private  fun observeViewModel() {
        upcomingViewModel.events.observe(viewLifecycleOwner) {eventList ->
            when (eventList) {
                is Result.Success -> {
                    showLoading(false)
                    eventAdapter.submitList(eventList.data.toMutableList())
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(context, "Error !!", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    showLoading(true)
                }
            }            }
    }

    private fun showLoading (isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}