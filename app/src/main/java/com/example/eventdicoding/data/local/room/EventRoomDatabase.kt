package com.example.eventdicoding.data.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.eventdicoding.data.local.entity.EventEntity
import com.example.eventdicoding.data.local.entity.FavoriteEntity

@Database(entities = [EventEntity::class, FavoriteEntity::class], version = 1)
abstract class EventRoomDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: EventRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): EventRoomDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    EventRoomDatabase::class.java, "favorite.db"
                ).build()
            }
    }
}