package com.spotify.demo.extensions

import androidx.appcompat.widget.SearchView

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