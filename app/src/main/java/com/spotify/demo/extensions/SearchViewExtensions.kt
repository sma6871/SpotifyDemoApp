package com.spotify.demo.extensions

import android.annotation.SuppressLint
import androidx.appcompat.widget.SearchView
import com.spotify.demo.utils.rx.RxSearchObservable
import java.util.concurrent.TimeUnit

fun SearchView.doAfterQueryTextChange(action: (newText: String) -> Boolean) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            return action.invoke(newText)
        }

    })
}

@SuppressLint("CheckResult")
fun SearchView.search(function: (query: String) -> Unit) {
    RxSearchObservable.fromView(this)
        .debounce(400, TimeUnit.MILLISECONDS)
        .observeUiSubscribeIo()
        .filter { x -> x.isNotEmpty() }
        .subscribe(
            { text ->
                function.invoke(text)
            },
            {
                it.printStackTrace()
            })
}