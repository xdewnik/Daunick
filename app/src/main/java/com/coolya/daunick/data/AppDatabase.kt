package com.coolya.daunick.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coolya.daunick.data.dao.DiaryDao
import com.coolya.daunick.data.entities.DiaryEvent

@Database(entities = [DiaryEvent::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract fun diaryDao(): DiaryDao
}