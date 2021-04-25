package com.coolya.daunick.ui

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.coolya.daunick.common.BaseViewModel
import com.coolya.daunick.common.SingleLiveEvent
import com.coolya.daunick.data.PdfUtility
import com.coolya.daunick.data.entities.DiaryEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DiaryEventViewModel(
    private val app: Application,
    private val repo: DiaryEventRepository,
    private val id: Long?
) :
    BaseViewModel(app) {

    val diaryEmoList: LiveData<List<String>> = repo.getDiaryEmoList()
    private val _diaryEvent: LiveData<DiaryEvent> = liveData {
        if (id != null) {
            emitSource(repo.getDiaryByIdLive(id))
        }
    }

    val diaryEvent: MutableLiveData<DiaryEvent> = MediatorLiveData<DiaryEvent>().apply {
        addSource(_diaryEvent) {
            this.value = it
        }
    }


    val diaryCreation = SingleLiveEvent<Unit>()
    val diaryPdfPath = SingleLiveEvent<String>()

    val diaryPagedList: LiveData<PagedList<DiaryEvent>> = LivePagedListBuilder<Int, DiaryEvent>(
        repo.getDiaryPagedList(),
        defaultPagedListConfig
    ).setFetchExecutor(defaultExecutor)
        .build()


    fun createOrUpdateDiary(
        edt_emo: String,
        edt_event: String,
        edt_scale: String,
        edt_thoughts: String,
        date: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val realDate = if (date != "-") {
                SimpleDateFormat("dd.MM HH:mm", Locale("ru")).run { parse(date).time }
            } else {
                System.currentTimeMillis()
            }
            val currentDiaryEvent = if (_diaryEvent.value == null) {
                DiaryEvent(edt_thoughts, edt_emo, edt_event, edt_scale, realDate)
            } else {
                diaryEvent.value!!
                    .apply {
                        thoughts = edt_thoughts
                        emo = edt_emo
                        event = edt_event
                        scale = edt_scale
                        time = realDate
                    }
            }
            diaryCreation.postValue(repo.createOrUpdateDiary(currentDiaryEvent))


        }
    }

    fun getSharedPdf() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repo.createPdf()
            diaryPdfPath.postValue(PdfUtility().createPDF(app, list))
        }
    }

    fun deleteEvent(event: DiaryEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteEvent(event)
        }
    }


}