package com.coolya.daunick.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.coolya.daunick.data.entities.DiaryEvent

@Dao
interface DiaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: DiaryEvent): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eventList: List<DiaryEvent>)

    @Update
    suspend fun update(event: DiaryEvent)

    @Update
    suspend fun update(eventList: List<DiaryEvent>)

    @Query("SELECT * FROM DiaryEvent ORDER BY time DESC")
    fun getDiaryEventPagedList(): DataSource.Factory<Int, DiaryEvent>

    @Query("SELECT * FROM DiaryEvent WHERE dairyId =:localId LIMIT 1")
    fun getDiaryEventLive(localId: Long): LiveData<DiaryEvent>

    @Delete
    suspend fun delete(event: DiaryEvent)

    @Query("SELECT * FROM DiaryEvent ORDER BY time ASC")
    suspend fun getDiaryEventListAsc(): List<DiaryEvent>

    @Query("SELECT DISTINCT emo FROM DiaryEvent")
    fun getDiaryEmoList(): LiveData<List<String>>


}