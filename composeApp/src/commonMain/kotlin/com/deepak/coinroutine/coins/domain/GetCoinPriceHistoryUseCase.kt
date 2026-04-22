package com.deepak.coinroutine.coins.domain

import com.deepak.coinroutine.coins.data.mapper.toPriceModel
import com.deepak.coinroutine.coins.domain.api.CoinsRemoteDataSource
import com.deepak.coinroutine.coins.domain.model.PriceModel
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.core.domain.map

class GetCoinPriceHistoryUseCase(
    private val client: CoinsRemoteDataSource
) {

    suspend fun execute(coinId: String): Result<List<PriceModel>, DataError.Remote> {
        return client.getPriceHistory(coinId).map { dto ->
            dto.history.map {
                it.toPriceModel()
            }
        }
    }
}