package com.spotify.demo.di

import com.spotify.demo.data.remote.AppService
import com.spotify.demo.data.repository.LocalRepository
import com.spotify.demo.data.repository.RemoteRepository
import com.spotify.demo.data.repository.Repository
import org.koin.dsl.module

val repositoryModule = module()
{
    single {
        Repository(
            get() as RemoteRepository,
            get() as LocalRepository
        )
    }

    single { RemoteRepository(get() as AppService) }
    single { LocalRepository() }

}