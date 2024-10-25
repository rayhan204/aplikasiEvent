package com.example.eventdicoding.data.local.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventsTable")
data class EventEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
    val id: String,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "summary")
    val summary: String? = null,

    @field:ColumnInfo(name = "mediaCover")
    val mediaCover: String,

    @field:ColumnInfo(name = "registrants")
    val registrants: Int? = null,

    @field:ColumnInfo(name = "imageLogo")
    val imageLogo: String? = null,

    @field:ColumnInfo(name = "link")
    val link: String? = null,

    @field:ColumnInfo(name = "description")
    val description: String? = null,

    @field:ColumnInfo(name = "ownerName")
    val ownerName: String? = null,

    @field:ColumnInfo(name = "cityName")
    val cityName: String? = null,

    @field:ColumnInfo(name = "quota")
    val quota: Int? = null,

    @field:ColumnInfo(name = "beginTime")
    val beginTime: String? = null,

    @field:ColumnInfo(name = "endTime")
    val endTime: String? = null,

    @field:ColumnInfo(name = "category")
    val category: String? = null,

    @field:ColumnInfo(name = "isActive")
    val isActive: Boolean
)
