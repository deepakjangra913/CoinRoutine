package com.deepak.coinroutine.coins.domain.api

import com.deepak.coinroutine.coins.data.remote.dto.CoinDetailsResponseDto
import com.deepak.coinroutine.coins.data.remote.dto.CoinPriceHistoryResponseDto
import com.deepak.coinroutine.coins.data.remote.dto.CoinsResponseDto
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.Result

/**
 * Interface defining the remote data operations for fetching coin-related data.
 */
interface CoinsRemoteDataSource {

    /**
     * Fetches a list of all available coins.
     *
     * @return A [Result] containing [CoinsResponseDto] or a [DataError.Remote].
     */
    suspend fun getListOfCoins(): Result<CoinsResponseDto, DataError.Remote>

    /**
     * Fetches the price history for a specific coin.
     *
     * @param coinId The unique identifier of the coin.
     * @return A [Result] containing [CoinPriceHistoryResponseDto] or a [DataError.Remote].
     */
    suspend fun getPriceHistory(coinId: String): Result<CoinPriceHistoryResponseDto, DataError.Remote>

    /**
     * Fetches the details for a specific coin by its ID.
     *
     * @param coinId The unique identifier of the coin.
     * @return A [Result] containing [CoinDetailsResponseDto] or a [DataError.Remote].
     */
    suspend fun getCoinById(coinId: String): Result<CoinDetailsResponseDto, DataError.Remote>

}