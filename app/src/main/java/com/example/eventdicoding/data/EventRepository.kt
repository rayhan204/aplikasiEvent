package com.example.eventdicoding.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.eventdicoding.data.local.entity.EventEntity
import com.example.eventdicoding.data.local.entity.FavoriteEntity
import com.example.eventdicoding.data.local.room.EventDao
import com.example.eventdicoding.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.await

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao
) {

    fun getDetailId(eventId: Int): LiveData<Result<EventEntity>> = liveData {
        emit(Result.Loading)
        try {
            val event = eventDao.getEventId(eventId)
            emit(Result.Success(event))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
        }
    }

    fun isEventFavorite(eventId: String): Flow<Boolean> {
        return  eventDao.isEventFavorite(eventId)
    }

    suspend fun addFavorite(favoriteId: String) {
        val favorite = FavoriteEntity(favoriteId)
        eventDao.insertFavorite(favorite)
    }

    suspend fun removeFavorite(favoriteId: String): Int {
        val favorite = FavoriteEntity(favoriteId)
        return eventDao.deleteFavorite(favorite)
    }

    fun getFavoriteEvents(): LiveData<Result<List<EventEntity>>> {
        return  eventDao.getFavoriteEvent()
            .map { events ->
                Result.Success(events)
            }
            .asLiveData()
    }

    fun getEventsUpcoming(): LiveData<Result<List<EventEntity>>> = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val localEvents = eventDao.getEventsUpcoming()
            if (localEvents.isNotEmpty()) {
                emit(Result.Success(localEvents))
            }
            val response = apiService.getUpcomingEvent().await()
            val events = response.listEvents.map{ eventDto ->
                EventEntity(
                    id = eventDto.id.toString(),
                    name = eventDto.name,
                    summary = eventDto.summary,
                    mediaCover = eventDto.mediaCover,
                    registrants = eventDto.registrants,
                    imageLogo = eventDto.imageLogo,
                    link = eventDto.link,
                    description = eventDto.description,
                    ownerName = eventDto.ownerName,
                    cityName = eventDto.cityName,
                    quota = eventDto.quota,
                    beginTime = eventDto.beginTime,
                    endTime = eventDto.endTime,
                    category = eventDto.category,
                    isActive = true
                )
            }

            eventDao.insertEvent(events)
            emit(Result.Success(events))
        }catch (e: Exception) {
            emit(Result.Error(e.message ?: "Failed to fetch events"))
        }
    }

    fun getFinishedEvents(): LiveData<Result<List<EventEntity>>> = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val localEvents = eventDao.getEventsFinished()
            if (localEvents.isNotEmpty()) {
                emit(Result.Success(localEvents))
            }
            val response = apiService.getFinishedEvent().await() // Ganti dengan endpoint yang sesuai
            val events = response.listEvents.map { eventDto ->
                EventEntity(
                    id = eventDto.id.toString(),
                    name = eventDto.name,
                    summary = eventDto.summary,
                    mediaCover = eventDto.mediaCover,
                    registrants = eventDto.registrants,
                    imageLogo = eventDto.imageLogo,
                    link = eventDto.link,
                    description = eventDto.description,
                    ownerName = eventDto.ownerName,
                    cityName = eventDto.cityName,
                    quota = eventDto.quota,
                    beginTime = eventDto.beginTime,
                    endTime = eventDto.endTime,
                    category = eventDto.category,
                    isActive = false
                )
            }

            eventDao.insertEvent(events)
            emit(Result.Success(events))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Failed to fetch finished events"))
        }

    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(apiService: ApiService, eventDao: EventDao): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository( apiService, eventDao)
            }.also { instance = it }
    }
}
