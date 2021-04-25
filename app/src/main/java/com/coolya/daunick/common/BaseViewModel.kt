package com.coolya.daunick.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.PagedList
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

open class BaseViewModel(app: Application) : AndroidViewModel(app) {
    protected val defaultPagedListConfig: PagedList.Config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPrefetchDistance(20)
        .setInitialLoadSizeHint(20)
        .setPageSize(20).build()

    protected val defaultExecutor: ExecutorService = Executors.newFixedThreadPool(5)
}