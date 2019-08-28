package com.spotify.demo.extensions

import com.spotify.demo.viewmodels.BaseViewModel
import io.reactivex.disposables.Disposable

fun BaseViewModel.addToDisposable(disposable: () -> Disposable) {
    compositeDisposable += disposable.invoke()
}
