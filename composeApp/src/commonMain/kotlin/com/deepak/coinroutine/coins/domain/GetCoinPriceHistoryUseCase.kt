package com.deepak.coinroutine.coins.domain

import com.deepak.coinroutine.coins.data.mapper.toPriceModel
import com.deepak.coinroutine.coins.domain.api.CoinsRemoteDataSource
import com.deepak.coinroutine.coins.domain.model.PriceModel
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.core.domain.map

/**
 * Use case for retrieving the price history of a specific coin.
 *
 * @property client The remote data source to fetch price history from.
 */
class GetCoinPriceHistoryUseCase(
    private val client: CoinsRemoteDataSource
) {

    /**
     * Executes the use case to fetch price history for a specific coin.
     *
     * @param coinId The unique identifier of the coin.
     * @return A [Result] containing a list of [PriceModel] on success, or a [DataError.Remote] on failure.
     */
    suspend fun execute(coinId: String): Result<List<PriceModel>, DataError.Remote> {
        return client.getPriceHistory(coinId).map { dto ->
            dto.data.history.map {
                it.toPriceModel()
            }
        }
    }
}