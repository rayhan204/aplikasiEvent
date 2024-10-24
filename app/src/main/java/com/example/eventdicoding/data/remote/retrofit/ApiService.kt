package com.example.eventdicoding.data.remote.retrofit

import com.example.eventdicoding.data.remote.response.EventDetailResponse
import com.example.eventdicoding.data.remote.response.EventResponse
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

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String
    ): Call<EventDetailResponse>

    @GET("/events")
    fun getEventSearch(
        @Query("q") query: String
    ): Call<EventResponse>

}