package com.ivasenko.mobilelivetest

import android.app.Application
import com.ivasenko.mobilelivetest.data.network.*
import com.ivasenko.mobilelivetest.data.repository.TweeterRepository
import com.ivasenko.mobilelivetest.data.repository.TweeterRepositoryImpl
import com.ivasenko.mobilelivetest.ui.details.TweetDetailsViewModelFactory
import com.ivasenko.mobilelivetest.ui.map.MapsViewModelFactory
import com.ivasenko.mobilelivetest.ui.search.SearchViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MobileLiveApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MobileLiveApplication))

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { TweeterApiService(instance()) }
        bind<TweeterNetworkDataSource>() with singleton { TweeterNetworkDataSourceImpl(instance()) }
        bind<TweeterRepository>() with singleton { TweeterRepositoryImpl(instance()) }
        bind() from provider { MapsViewModelFactory(instance()) }
        bind() from provider { TweetDetailsViewModelFactory(instance()) }
        bind() from provider { SearchViewModelFactory(instance()) }

    }
}