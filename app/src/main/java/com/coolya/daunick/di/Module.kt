package com.coolya.daunick.di

import androidx.room.Room
import com.coolya.daunick.data.AppDatabase
import com.coolya.daunick.ui.DiaryEventLocalDataSource
import com.coolya.daunick.ui.DiaryEventLocalDataSourceImpl
import com.coolya.daunick.ui.DiaryEventRepository
import com.coolya.daunick.ui.DiaryEventViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val DataBaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "daunick.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}

val DiaryEventModule = module {
    factory<DiaryEventLocalDataSource> { DiaryEventLocalDataSourceImpl(get()) }
    factory { DiaryEventRepository(get()) }
    viewModel { (id: Long?) -> DiaryEventViewModel(get(), get(), id) }
}

