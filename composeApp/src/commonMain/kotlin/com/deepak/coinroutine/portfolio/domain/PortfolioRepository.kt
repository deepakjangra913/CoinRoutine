package com.deepak.coinroutine.portfolio.domain

import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.EmptyResult
import com.deepak.coinroutine.core.domain.Result
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the operations for managing the user's portfolio and balance.
 */
interface PortfolioRepository {

    /**
     * Initializes the user's balance if it hasn't been set yet.
     */
    suspend fun initializeBalance()

    /**
     * Provides a flow of the user's portfolio coins, including remote data updates.
     *
     * @return A [Flow] of [Result] containing a list of [PortfolioCoinModel].
     */
    fun allPortFolioCoinsFlow(): Flow<Result<List<PortfolioCoinModel>, DataError.Remote>>

    /**
     * Retrieves a specific coin from the portfolio by its ID.
     *
     * @param coinId The unique identifier of the coin.
     * @return A [Result] containing the [PortfolioCoinModel] or null if not found.
     */
    suspend fun getPortfolioCoin(coinId: String): Result<PortfolioCoinModel?, DataError.Remote>

    /**
     * Saves or updates a coin in the user's portfolio.
     *
     * @param portfolioCoinModel The coin model to save.
     * @return An [EmptyResult] indicating success or a [DataError.Local] on failure.
     */
    suspend fun savePortfolioCoin(portfolioCoinModel: PortfolioCoinModel): EmptyResult<DataError.Local>

    /**
     * Removes a coin from the user's portfolio.
     *
     * @param coinId The unique identifier of the coin to remove.
     */
    suspend fun removeCoinFromPortfolio(coinId: String)

    /**
     * Calculates and provides a flow of the total value of the portfolio in fiat.
     *
     * @return A [Flow] of [Result] containing the total portfolio value.
     */
    fun calculateTotalPortfolioValue(): Flow<Result<Double, DataError.Remote>>

    /**
     * Provides a flow of the total balance (cash + portfolio value).
     *
     * @return A [Flow] of [Result] containing the total balance.
     */
    fun totalBalanceFlow(): Flow<Result<Double, DataError.Remote>>

    /**
     * Provides a flow of the user's available cash balance.
     *
     * @return A [Flow] of the cash balance as a [Double].
     */
    fun cashBalanceFlow(): Flow<Double>

    /**
     * Updates the user's cash balance to a new value.
     *
     * @param newBalance The new cash balance.
     */
    suspend fun updateCashBalance(newBalance: Double)
}