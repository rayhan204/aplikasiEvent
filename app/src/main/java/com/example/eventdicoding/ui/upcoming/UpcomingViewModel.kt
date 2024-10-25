package com.example.eventdicoding.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.eventdicoding.data.EventRepository
import com.example.eventdicoding.data.Result
import com.example.eventdicoding.data.local.entity.EventEntity


class UpcomingViewModel(eventRepository: EventRepository) : ViewModel() {

    val events: LiveData<Result<List<EventEntity>>> = eventRepository.getEventsUpcoming()

}