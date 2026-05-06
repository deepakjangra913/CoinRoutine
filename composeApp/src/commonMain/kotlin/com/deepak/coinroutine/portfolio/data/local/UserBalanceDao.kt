package com.deepak.coinroutine.portfolio.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

/**
 * Data Access Object for managing the user's cash balance in the local database.
 * This DAO typically operates on a single record with a constant ID.
 */
@Dao
interface UserBalanceDao {

    /**
     * Retrieves the current cash balance of the user.
     *
     * @return The cash balance as a [Double], or null if not set.
     */
    @Query("SELECT cashBalance FROM UserBalanceEntity WHERE id = 1")
    suspend fun getCashBalance(): Double?

    /**
     * Inserts or updates the user balance record.
     *
     * @param userBalanceEntity The entity containing the balance information.
     */
    @Upsert
    suspend fun insertBalance(userBalanceEntity: UserBalanceEntity)

    /**
     * Updates the user's cash balance.
     *
     * @param newBalance The new balance amount.
     */
    @Query("UPDATE UserBalanceEntity SET cashBalance = :newBalance WHERE id = 1")
    suspend fun updateCashBalance(newBalance: Double)
}