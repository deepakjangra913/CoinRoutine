package com.deepak.coinroutine.di

import androidx.room.RoomDatabase
import com.deepak.coinroutine.database.portfolio.PortfolioDatabase
import com.deepak.coinroutine.database.portfolio.getPortfolioDatabaseBuilder
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    single<HttpClientEngine> { Darwin.create() }
    singleOf(::getPortfolioDatabaseBuilder).bind<RoomDatabase.Builder<PortfolioDatabase>>()
}