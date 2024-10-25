package com.example.eventdicoding.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.eventdicoding.data.EventRepository
import com.example.eventdicoding.data.Result
import com.example.eventdicoding.data.local.entity.EventEntity

class HomeViewModel(eventRepository: EventRepository) : ViewModel() {

    val eventUpcoming : LiveData<Result<List<EventEntity>>> = eventRepository.getEventsUpcoming()

    val eventFinished : LiveData<Result<List<EventEntity>>> = eventRepository.getFinishedEvents()
}