package com.deepak.coinroutine.coins.domain

import com.deepak.coinroutine.coins.data.mapper.toCoinModel
import com.deepak.coinroutine.coins.domain.api.CoinsRemoteDataSource
import com.deepak.coinroutine.coins.domain.model.CoinModel
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.core.domain.map

class GetCoinDetailsUseCase(
    private val client: CoinsRemoteDataSource
) {

    suspend fun execute(coinId: String): Result<CoinModel, DataError.Remote> {
        return client.getCoinById(coinId).map { dto ->
            dto.data.coin.toCoinModel()
        }
    }
}