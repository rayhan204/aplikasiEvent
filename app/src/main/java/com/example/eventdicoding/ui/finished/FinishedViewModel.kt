package com.example.eventdicoding.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.eventdicoding.data.EventRepository
import com.example.eventdicoding.data.Result
import com.example.eventdicoding.data.local.entity.EventEntity


class FinishedViewModel(eventRepository: EventRepository) : ViewModel() {

    val events: LiveData<Result<List<EventEntity>>> = eventRepository.getFinishedEvents()

}