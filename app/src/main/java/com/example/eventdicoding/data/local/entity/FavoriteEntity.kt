package com.example.eventdicoding.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favoriteTable")
@Parcelize
data class FavoriteEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String = "",

    @ColumnInfo(name = "name")
    var name: String ="",

    @ColumnInfo(name = "mediaCover")
    var mediaCover: String = ""
): Parcelable