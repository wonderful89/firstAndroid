package com.example.firstAndroid.functions.logic.network

import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers

object RxSchedulers {
    private fun mainThread(): Scheduler? {
        return RxAndroidPlugins.onMainThreadScheduler(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
    }

    fun <T> compose(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(mainThread())
        }
    }

    fun <T> io(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
        }
    }
}