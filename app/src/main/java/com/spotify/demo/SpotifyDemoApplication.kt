package com.spotify.demo

import android.app.Application
import com.spotify.demo.di.remoteModule
import com.spotify.demo.di.repositoryModule
import com.spotify.demo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpotifyDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SpotifyDemoApplication)
            this.modules(
                remoteModule +
                        viewModelModule +
                        repositoryModule
            )
        }
    }

}