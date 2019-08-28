package com.spotify.demo.di

import com.spotify.demo.data.remote.AppService
import com.spotify.demo.data.remote.AuthenticatorInterceptor
import com.spotify.demo.data.repository.LocalRepository
import com.spotify.demo.data.repository.RemoteRepository
import com.spotify.demo.data.repository.Repository
import com.spotify.demo.utils.SharedPrefHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module()
{
    single {
        Repository(
            get() as RemoteRepository,
            get() as LocalRepository,
            get() as AuthenticatorInterceptor
        )
    }

    single { RemoteRepository(get() as AppService) }
    single { LocalRepository() }
    single { SharedPrefHelper(androidContext()).defaultPrefs() }


}