package com.example.eventdicoding.data.retrofit

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
}