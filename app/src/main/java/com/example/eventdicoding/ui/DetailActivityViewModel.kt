package com.example.eventdicoding.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventdicoding.data.EventRepository
import com.example.eventdicoding.data.local.entity.EventEntity
import com.example.eventdicoding.data.Result
import kotlinx.coroutines.launch

class DetailActivityViewModel(private val repository: EventRepository) : ViewModel() {
    private val _event = MutableLiveData<Result<EventEntity>>()
    val event: LiveData<Result<EventEntity>> = _event

   private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite


    fun getDetailId(eventId: Int) {
        viewModelScope.launch {
            repository.getDetailId(eventId).observeForever { result ->
                _event.value = result
                if (result is Result.Success) {
                    checkStatusFavorite(result.data.id)
                }
            }
        }
    }

    private fun checkStatusFavorite(eventId: String) {
        viewModelScope.launch {
            repository.isEventFavorite(eventId).collect { isFavorite ->
                _isFavorite.value = isFavorite
            }
        }
    }

    fun buttonFavorite(event: EventEntity, context: Context) {
        viewModelScope.launch {
            val nowFavoriteStatus = _isFavorite.value ?: false
            if (nowFavoriteStatus) {
                repository.removeFavorite(event.id)
                Toast.makeText(context, "Dihapus dari favorite", Toast.LENGTH_SHORT).show()
            } else {
                repository.addFavorite(event.id)
                Toast.makeText(context, "Ditambah ke Favorite", Toast.LENGTH_SHORT).show()
            }
            _isFavorite.value = !nowFavoriteStatus
        }
    }

}