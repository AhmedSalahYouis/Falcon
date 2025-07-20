package com.salah.falcon.app.di

import com.apollographql.apollo3.ApolloClient
import com.salah.falcon.app.logger.ITimberLogger
import com.salah.falcon.app.logger.TimberLogger
import com.salah.falcon.core.data.util.ConnectivityManagerNetworkMonitor
import com.salah.falcon.core.data.util.NetworkMonitor
import com.salah.falcon.data.datasource.ILaunchesRemoteDataSource
import com.salah.falcon.data.datasource.LaunchesDataSourceImpl
import com.salah.falcon.data.mapper.IRemoteLaunchToLaunchSummaryMapper
import com.salah.falcon.data.mapper.LaunchesToLaunchSummaryMapper
import com.salah.falcon.data.repository.LaunchesRepository
import com.salah.falcon.domain.repository.ILaunchesRepository
import com.salah.falcon.domain.usecase.GetLaunchListUseCase
import com.salah.falcon.presentation.launches.listing.LaunchesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

// Core Modules
val appModule = module {
    // Logging
    single<ITimberLogger> { TimberLogger() }

    // Network Client
    single {
        ApolloClient.Builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .build()
    }

    // Network Monitoring
    single<NetworkMonitor> { ConnectivityManagerNetworkMonitor(get()) }
}

// Data Modules
val dataModule = module {
    // Mappers
    single<IRemoteLaunchToLaunchSummaryMapper> { LaunchesToLaunchSummaryMapper() }

    // DataSources
    single<ILaunchesRemoteDataSource> { LaunchesDataSourceImpl(get(), get(), get()) }

    // Repositories
    single<ILaunchesRepository> { LaunchesRepository(get()) }
}
// Domain Modules
val domainModule = module {
    // UseCases
    single<GetLaunchListUseCase> { GetLaunchListUseCase(get()) }

}

// Presentation Modules
val viewModelModule = module {
    viewModel { LaunchesViewModel(get()) }
}
