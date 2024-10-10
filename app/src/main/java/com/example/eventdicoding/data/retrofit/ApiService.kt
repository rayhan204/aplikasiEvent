package com.example.eventdicoding.data.retrofit

import com.example.eventdicoding.data.response.EventDetailResponse
import com.example.eventdicoding.data.response.EventResponse
import  retrofit2.Call
import  retrofit2.http.*


interface ApiService {
    @GET("events")
    fun getUpcomingEvent(
        @Query("active") active: Int = 1
    ): Call<EventResponse>

    @GET("events")
    fun getFinishedEvent(
        @Query("active") active: Int = 0
    ): Call<EventResponse>

    @GET ("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String
    ): Call<EventDetailResponse>

    @GET("/events")
    suspend fun getEventSearch(
        @Query("active") active: Int = -1,
        @Query("q") query: String
    ): Call<EventResponse>

    @GET("/events")
    suspend fun getEventReminder(
        @Query("active") active: Int = 1,
        @Query("limit") limit: Int = 1
    ): Call<EventResponse>
}