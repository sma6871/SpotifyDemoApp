package com.spotify.demo.utils.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object SchedulerProvider {

    fun io() = Schedulers.io()

    fun ui() = AndroidSchedulers.mainThread()

    fun computation() = Schedulers.computation()

}