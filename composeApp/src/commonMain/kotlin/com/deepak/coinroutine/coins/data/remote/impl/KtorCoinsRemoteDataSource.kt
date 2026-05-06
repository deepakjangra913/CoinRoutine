package com.deepak.coinroutine.coins.data.remote.impl

import com.deepak.coinroutine.coins.data.remote.dto.CoinDetailsResponseDto
import com.deepak.coinroutine.coins.data.remote.dto.CoinPriceHistoryResponseDto
import com.deepak.coinroutine.coins.data.remote.dto.CoinsResponseDto
import com.deepak.coinroutine.coins.domain.api.CoinsRemoteDataSource
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.core.network.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get

private const val BASE_URL = "https://api.coinranking.com/v2"

/**
 * Ktor-based implementation of [CoinsRemoteDataSource] for fetching coin data from the CoinRanking API.
 *
 * @property httpClient The [HttpClient] used to make network requests.
 */
class KtorCoinsRemoteDataSource(
    private val httpClient: HttpClient
) : CoinsRemoteDataSource {

    /**
     * Fetches a list of coins from the remote API.
     *
     * @return A [Result] containing [CoinsResponseDto] on success or [DataError.Remote] on failure.
     */
    override suspend fun getListOfCoins(): Result<CoinsResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/coins")
        }
    }

    /**
     * Fetches price history for a specific coin from the remote API.
     *
     * @param coinId The unique identifier of the coin.
     * @return A [Result] containing [CoinPriceHistoryResponseDto] on success or [DataError.Remote] on failure.
     */
    override suspend fun getPriceHistory(coinId: String): Result<CoinPriceHistoryResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/coin/$coinId/history")
        }
    }

    /**
     * Fetches details for a specific coin by its ID from the remote API.
     *
     * @param coinId The unique identifier of the coin.
     * @return A [Result] containing [CoinDetailsResponseDto] on success or [DataError.Remote] on failure.
     */
    override suspend fun getCoinById(coinId: String): Result<CoinDetailsResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/coin/$coinId")
        }
    }
}