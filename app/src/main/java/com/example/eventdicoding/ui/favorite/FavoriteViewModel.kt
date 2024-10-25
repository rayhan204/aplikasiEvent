package com.example.eventdicoding.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventdicoding.data.EventRepository
import com.example.eventdicoding.data.local.entity.EventEntity
import com.example.eventdicoding.data.Result
import kotlinx.coroutines.launch

class FavoriteViewModel(private val eventRepository: EventRepository): ViewModel() {

    val favoriteEvents: LiveData<Result<List<EventEntity>>> = eventRepository.getFavoriteEvents()

    fun getUpcomingEvents() = eventRepository.getEventsUpcoming()

    fun getFinishedEvents() = eventRepository.getFinishedEvents()

    fun removeFavorite(eventId: String) {
        viewModelScope.launch {
            eventRepository.removeFavorite(eventId)
        }
    }

    fun addFavorite(eventId: String) {
        viewModelScope.launch {
            eventRepository.addFavorite(eventId)
        }
    }
}