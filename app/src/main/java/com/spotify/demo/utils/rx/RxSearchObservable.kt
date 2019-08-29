package com.spotify.demo.utils.rx


import androidx.appcompat.widget.SearchView
import com.spotify.demo.extensions.doAfterQueryTextChange
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class RxSearchObservable {
    companion object {

        fun fromView(searchEditText: SearchView): Observable<String> {

            val subject = PublishSubject.create<String>()

            searchEditText.doAfterQueryTextChange { text ->
                subject.onNext(text)
                return@doAfterQueryTextChange false
            }

            return subject
        }
    }
}