package com.deepak.coinroutine.database.portfolio

import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object PortfolioDatabaseCreator :
    RoomDatabaseConstructor<PortfolioDatabase>

fun getPortFolioDatabase(builder: RoomDatabase.Builder<PortfolioDatabase>): PortfolioDatabase{
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}