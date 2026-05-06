package com.deepak.coinroutine.portfolio.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for managing portfolio coins in the local database.
 * Provides methods for performing CRUD operations on [PortfolioCoinEntity].
 */
@Dao
interface PortfolioDao {

    /**
     * Inserts or updates a coin in the portfolio.
     *
     * @param portfolioCoinEntity The coin entity to be inserted or updated.
     */
    @Upsert
    suspend fun insert(portfolioCoinEntity: PortfolioCoinEntity)

    /**
     * Retrieves all coins owned in the portfolio, ordered by timestamp in descending order.
     *
     * @return A [Flow] emitting a list of all [PortfolioCoinEntity] entries.
     */
    @Query("SELECT * FROM PortfolioCoinEntity ORDER BY timeStamp DESC")
    fun getAllOwnedCoins(): Flow<List<PortfolioCoinEntity>>

    /**
     * Retrieves a specific coin from the portfolio by its unique identifier.
     *
     * @param coinId The unique identifier of the coin.
     * @return The [PortfolioCoinEntity] matching the [coinId].
     */
    @Query("SELECT * FROM PortfolioCoinEntity WHERE coinId = :coinId")
    suspend fun getCoinById(coinId: String): PortfolioCoinEntity

    /**
     * Deletes a coin from the portfolio by its unique identifier.
     *
     * @param coinId The unique identifier of the coin to be deleted.
     */
    @Query("DELETE FROM PortfolioCoinEntity WHERE coinId = :coinId")
    suspend fun deletePortfolioItem(coinId: String)
}