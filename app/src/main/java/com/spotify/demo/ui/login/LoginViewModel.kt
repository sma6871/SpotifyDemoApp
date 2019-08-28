package com.spotify.demo.ui.login

import com.spotify.demo.data.repository.Repository
import com.spotify.demo.viewmodels.BaseViewModel

class LoginViewModel(private val repository: Repository):BaseViewModel() {
    fun updateToken(accessToken: String) {
        repository.updateToken(accessToken)
    }

}