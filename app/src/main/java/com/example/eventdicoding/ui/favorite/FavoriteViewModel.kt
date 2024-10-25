package com.example.eventdicoding.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.eventdicoding.data.EventRepository
import com.example.eventdicoding.data.local.entity.EventEntity
import com.example.eventdicoding.data.Result

class FavoriteViewModel(eventRepository: EventRepository): ViewModel() {

    val favoriteEvents: LiveData<Result<List<EventEntity>>> = eventRepository.getFavoriteEvents()

}