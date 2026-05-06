package com.deepak.coinroutine.database.portfolio

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.deepak.coinroutine.portfolio.data.local.PortfolioCoinEntity
import com.deepak.coinroutine.portfolio.data.local.PortfolioDao
import com.deepak.coinroutine.portfolio.data.local.UserBalanceDao
import com.deepak.coinroutine.portfolio.data.local.UserBalanceEntity

/**
 * The Room database for the portfolio, containing tables for coins and user balance.
 */
@ConstructedBy(PortfolioDatabaseCreator::class)
@Database(entities = [PortfolioCoinEntity::class, UserBalanceEntity::class], version = 2)
abstract class PortfolioDatabase: RoomDatabase() {
    /**
     * Provides access to portfolio-related database operations.
     */
    abstract fun portfolioDao(): PortfolioDao

    /**
     * Provides access to user balance-related database operations.
     */
    abstract fun userBalanceDao(): UserBalanceDao
}