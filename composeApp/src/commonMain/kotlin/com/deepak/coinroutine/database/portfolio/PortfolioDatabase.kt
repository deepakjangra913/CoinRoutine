package com.deepak.coinroutine.database.portfolio

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deepak.coinroutine.portfolio.data.local.PortfolioCoinEntity
import com.deepak.coinroutine.portfolio.data.local.PortfolioDao

@Database(entities = [PortfolioCoinEntity::class], version = 1)
abstract class PortfolioDatabase: RoomDatabase() {
    abstract fun portfolioDao(): PortfolioDao
}