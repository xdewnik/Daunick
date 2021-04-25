package com.coolya.daunick.ui

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.coolya.daunick.data.AppDatabase
import com.coolya.daunick.data.entities.DiaryEvent


interface DiaryEventLocalDataSource {

    fun getDiaryEventPagedList(): DataSource.Factory<Int, DiaryEvent>
    fun getDiaryEventByIdLiveData(localId: Long): LiveData<DiaryEvent>
    suspend fun createDiaryEvent(event: DiaryEvent)
    suspend fun updateDiaryEvent(event:DiaryEvent)
    suspend fun deleteDiaryEvent(event: DiaryEvent)
    suspend fun getDiaryEventListAsc(): List<DiaryEvent>
    fun getDiaryEmoList(): LiveData<List<String>>

}

class DiaryEventLocalDataSourceImpl(private val db: AppDatabase) : DiaryEventLocalDataSource {
    override fun getDiaryEventPagedList(): DataSource.Factory<Int, DiaryEvent> =
        db.diaryDao().getDiaryEventPagedList()

    override fun getDiaryEventByIdLiveData(localId: Long): LiveData<DiaryEvent> =
        db.diaryDao().getDiaryEventLive(localId)

    override suspend fun createDiaryEvent(event: DiaryEvent) {
        db.diaryDao().insert(event)
    }

    override suspend fun updateDiaryEvent(event: DiaryEvent) = db.diaryDao().update(event
    )

    override suspend fun deleteDiaryEvent(event: DiaryEvent) = db.diaryDao().delete(event)
    override suspend fun getDiaryEventListAsc(): List<DiaryEvent> = db.diaryDao().getDiaryEventListAsc()
    override fun getDiaryEmoList(): LiveData<List<String>> = db.diaryDao().getDiaryEmoList()

}