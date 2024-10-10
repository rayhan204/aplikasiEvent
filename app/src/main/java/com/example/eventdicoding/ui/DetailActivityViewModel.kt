package com.example.eventdicoding.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventdicoding.data.response.Event
import com.example.eventdicoding.data.response.EventDetailResponse
import com.example.eventdicoding.data.retrofit.ApiConfig
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback
class DetailActivityViewModel : ViewModel() {
    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadEventDetail(eventId: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailEvent(eventId.toString())

        client.enqueue(object : Callback<EventDetailResponse> {
            override fun onResponse(
                call: Call<EventDetailResponse>,
                response: Response<EventDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    // Make sure the response body is not null
                    response.body()?.let { eventDetailResponse ->
                        _event.value = eventDetailResponse.event
                    } ?: run {
                        Log.e(TAG, "Response body is null")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailActivityViewModel"
    }
}