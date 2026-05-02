package com.deepak.coinroutine.di

import androidx.room.RoomDatabase
import com.deepak.coinroutine.coins.data.remote.impl.KtorCoinsRemoteDataSource
import com.deepak.coinroutine.coins.domain.GetCoinDetailsUseCase
import com.deepak.coinroutine.coins.domain.GetCoinPriceHistoryUseCase
import com.deepak.coinroutine.coins.domain.GetCoinsListUseCase
import com.deepak.coinroutine.coins.domain.api.CoinsRemoteDataSource
import com.deepak.coinroutine.coins.presentation.CoinsListViewModel
import com.deepak.coinroutine.core.network.HttpClientFactory
import com.deepak.coinroutine.database.portfolio.PortfolioDatabase
import com.deepak.coinroutine.database.portfolio.getPortFolioDatabase
import com.deepak.coinroutine.portfolio.data.PortfolioRepositoryImpl
import com.deepak.coinroutine.portfolio.domain.PortfolioRepository
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(
    config: KoinAppDeclaration? = null
) = startKoin {

    config?.invoke(this)
    modules(
        platformModule,
        sharedModule
    )
}

expect val platformModule: Module

val sharedModule = module {

    // core
    single { HttpClientFactory.create(get()) }

    // portfolio
    single {
        getPortFolioDatabase(get<RoomDatabase.Builder<PortfolioDatabase>>())
    }

    singleOf(::PortfolioRepositoryImpl).bind<PortfolioRepository>()

    // coins list
    viewModel { CoinsListViewModel(get(), get()) }
    singleOf(::GetCoinsListUseCase)
    singleOf(::GetCoinPriceHistoryUseCase)
    singleOf(::KtorCoinsRemoteDataSource).bind<CoinsRemoteDataSource>()
    singleOf(::GetCoinDetailsUseCase)
}