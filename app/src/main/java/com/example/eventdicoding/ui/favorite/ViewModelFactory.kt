package com.example.eventdicoding.ui.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eventdicoding.data.EventRepository
import com.example.eventdicoding.di.Injection
import com.example.eventdicoding.ui.DetailActivityViewModel
import com.example.eventdicoding.ui.finished.FinishedViewModel
import com.example.eventdicoding.ui.home.HomeViewModel
import com.example.eventdicoding.ui.settings.SettingViewModel
import com.example.eventdicoding.ui.upcoming.UpcomingViewModel

class ViewModelFactory private constructor(private val eventRepository: EventRepository):
ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailActivityViewModel::class.java)) {
            return DetailActivityViewModel(eventRepository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(eventRepository) as T
        } else if (modelClass.isAssignableFrom(UpcomingViewModel::class.java)) {
            return UpcomingViewModel(eventRepository) as T
        } else if (modelClass.isAssignableFrom(FinishedViewModel::class.java)) {
            return FinishedViewModel(eventRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

}