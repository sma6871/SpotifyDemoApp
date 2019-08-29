package com.spotify.demo.di

import com.spotify.demo.data.repository.Repository
import com.spotify.demo.ui.list.ListViewModel
import com.spotify.demo.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ListViewModel(get() as Repository) }
    viewModel { LoginViewModel(get() as Repository) }

}