package com.example.eventdicoding.data.local.room

import androidx.room.*
import com.example.eventdicoding.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import com.example.eventdicoding.data.local.entity.EventEntity

@Dao
interface EventDao {

    @Query("SELECT * FROM eventsTable WHERE id IN (SELECT id FROM favoriteTable)")
    fun getFavoriteEvent(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvent(eventsTable: List<EventEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity): Int

    @Query("SELECT * FROM eventsTable WHERE id = :eventId LIMIT 1")
    suspend fun getEventId(eventId: kotlin.Int): EventEntity

    @Query("SELECT EXISTS(SELECT * FROM favoriteTable WHERE id = :eventId)")
    fun isEventFavorite(eventId: String): Flow<Boolean>

}