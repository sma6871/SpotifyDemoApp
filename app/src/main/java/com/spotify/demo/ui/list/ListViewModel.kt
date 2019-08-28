package com.spotify.demo.ui.list

import com.spotify.demo.data.repository.Repository
import com.spotify.demo.extensions.addToDisposable
import com.spotify.demo.viewmodels.BaseViewModel
import io.reactivex.subjects.BehaviorSubject

class ListViewModel(private val repository: Repository) : BaseViewModel() {

    val listActivityState = BehaviorSubject.create<ListActivityState>()
    private var totalPages = 0
    private var selectedYear = -1

    fun initMovies(year: Int = -1) {
        selectedYear = year
        //go to loading state
        listActivityState.onNext(Loading(false))
        addToDisposable {
            repository.discoverMovies(year).subscribe(
                {
                    totalPages = it.totalPages ?: 0
                    // show result list or empty list if the result was null
                    listActivityState.onNext(
                        SuccessLoading(
                            it.results ?: listOf()
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

    fun loadMore(newPageIndex: Int) {
        if (totalPages >= newPageIndex) {
            listActivityState.onNext(Loading(true))
            addToDisposable {
                repository.discoverMovies(selectedYear, newPageIndex).subscribe(
                    {
                        totalPages = it.totalPages ?: 0
                        // show result list or empty list if the result was null
                        listActivityState.onNext(
                            SuccessLoadingMore(
                                it.results ?: listOf()
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

}