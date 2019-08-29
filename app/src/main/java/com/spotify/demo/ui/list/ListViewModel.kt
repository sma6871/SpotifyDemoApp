package com.spotify.demo.ui.list

import com.spotify.demo.data.repository.Repository
import com.spotify.demo.extensions.addToDisposable
import com.spotify.demo.viewmodels.BaseViewModel
import io.reactivex.subjects.BehaviorSubject

class ListViewModel(private val repository: Repository) : BaseViewModel() {

    val listActivityState = BehaviorSubject.create<ListActivityState>()


    fun searchArtists(query: String) {
        //go to loading state
        listActivityState.onNext(Loading)
        addToDisposable {
            repository.searchArtists(query).subscribe(
                {
                    // show result list or empty list if the result was null
                    listActivityState.onNext(
                        SuccessLoading(
                            it.artists?.items ?: listOf()
                        )
                    )
                },
                {
                    listActivityState.onNext(
                        ErrorLoading(
                            it.localizedMessage ?: ""
                        )
                    )
                    listActivityState.onNext(Init)
                }
            )
        }
    }


}