package com.example.eventdicoding.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eventdicoding.databinding.FragmentFinishedBinding
import com.example.eventdicoding.ui.EventAdapter

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null

    private val binding get() = _binding!!

    private lateinit var eventAdapter: EventAdapter
    private lateinit var finishedViewModel: FinishedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi ViewModel
        finishedViewModel = ViewModelProvider(this)[FinishedViewModel::class.java]

        // Inisialisasi View Binding
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)

        // Inisialisasi RecyclerView dan Adapter
        eventAdapter = EventAdapter()
        binding.rvEvents.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = eventAdapter
        }

        // Observasi perubahan data dari ViewModel
        finishedViewModel.events.observe(viewLifecycleOwner) { eventList ->
            eventAdapter.submitList(eventList)
            binding.progressBar.visibility = View.GONE // Sembunyikan ProgressBar saat data telah dimuat
        }

        // Observasi status loading
        finishedViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}