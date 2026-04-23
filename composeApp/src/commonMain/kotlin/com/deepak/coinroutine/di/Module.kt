package com.deepak.coinroutine.di

import com.deepak.coinroutine.core.network.HttpClientFactory
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
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

    single { HttpClientFactory.create(get()) }
}