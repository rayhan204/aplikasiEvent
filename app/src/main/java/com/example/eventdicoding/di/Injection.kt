package com.example.eventdicoding.di

import android.content.Context
import com.example.eventdicoding.data.EventRepository
import com.example.eventdicoding.data.local.room.EventRoomDatabase
import com.example.eventdicoding.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventRoomDatabase.getDatabase(context)
        val dao = database.eventDao()
        return EventRepository.getInstance(apiService,dao)
    }
}