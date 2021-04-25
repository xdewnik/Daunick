package com.coolya.daunick.ui

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.coolya.daunick.data.entities.DiaryEvent

class DiaryEventRepository(private val diaryEventLocalDataSource: DiaryEventLocalDataSource) {


    fun getDiaryPagedList(): DataSource.Factory<Int, DiaryEvent> =
        diaryEventLocalDataSource.getDiaryEventPagedList()

    fun getDiaryByIdLive(id: Long): LiveData<DiaryEvent> =
        diaryEventLocalDataSource.getDiaryEventByIdLiveData(id)

    suspend fun createOrUpdateDiary(currentDiaryEvent: DiaryEvent) {
        if (currentDiaryEvent.dairyId == 0L) {
            diaryEventLocalDataSource.createDiaryEvent(currentDiaryEvent)
        } else {
            diaryEventLocalDataSource.updateDiaryEvent(currentDiaryEvent)
        }
    }

    suspend fun deleteEvent(event: DiaryEvent) = diaryEventLocalDataSource.deleteDiaryEvent(event)
    suspend fun createPdf() = diaryEventLocalDataSource.getDiaryEventListAsc()
    fun getDiaryEmoList(): LiveData<List<String>> = diaryEventLocalDataSource.getDiaryEmoList()

}
