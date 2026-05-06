package com.deepak.coinroutine.coins.domain

import com.deepak.coinroutine.coins.data.mapper.toCoinModel
import com.deepak.coinroutine.coins.domain.api.CoinsRemoteDataSource
import com.deepak.coinroutine.coins.domain.model.CoinModel
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.core.domain.map

/**
 * Use case for retrieving details of a specific coin by its unique identifier.
 *
 * @property client The remote data source to fetch coin details from.
 */
class GetCoinDetailsUseCase(
    private val client: CoinsRemoteDataSource
) {

    /**
     * Executes the use case to fetch details for a specific coin.
     *
     * @param coinId The unique identifier of the coin.
     * @return A [Result] containing the [CoinModel] on success, or a [DataError.Remote] on failure.
     */
    suspend fun execute(coinId: String): Result<CoinModel, DataError.Remote> {
        return client.getCoinById(coinId).map { dto ->
            dto.data.coin.toCoinModel()
        }
    }
}