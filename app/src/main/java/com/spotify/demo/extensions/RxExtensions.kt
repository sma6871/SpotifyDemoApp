package com.spotify.demo.extensions

import com.spotify.demo.utils.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

/**
 * Use SchedulerProvider configuration for Completable
 */
fun Completable.observeUiSubscribeIo(): Completable =
    this.observeOn(SchedulerProvider.ui()).subscribeOn(SchedulerProvider.io())

/**
 * Use SchedulerProvider configuration for Single
 */
fun <T> Single<T>.observeUiSubscribeIo(): Single<T> =
    this.observeOn(SchedulerProvider.ui()).subscribeOn(SchedulerProvider.io())

/**
 * Use SchedulerProvider configuration for Observable
 */
fun <T> Observable<T>.observeUiSubscribeIo(): Observable<T> =
    this.observeOn(SchedulerProvider.ui()).subscribeOn(SchedulerProvider.io())




