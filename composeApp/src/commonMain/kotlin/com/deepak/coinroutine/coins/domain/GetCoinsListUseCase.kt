package com.deepak.coinroutine.coins.domain

import com.deepak.coinroutine.coins.data.mapper.toCoinModel
import com.deepak.coinroutine.coins.domain.api.CoinsRemoteDataSource
import com.deepak.coinroutine.coins.domain.model.CoinModel
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.core.domain.map

/**
 * Use case for retrieving a list of coins from the remote data source.
 *
 * @property client The remote data source to fetch coin data from.
 */
class GetCoinsListUseCase(
    private val client: CoinsRemoteDataSource
) {

    /**
     * Executes the use case to fetch a list of coins.
     *
     * @return A [Result] containing either a list of [CoinModel] on success,
     * or a [DataError.Remote] on failure.
     */
    suspend fun execute(): Result<List<CoinModel>, DataError.Remote> {
        return client.getListOfCoins().map { dto ->
            dto.data.coins.map { it.toCoinModel() }
        }
    }
}