package com.deepak.coinroutine.coins.domain

import com.deepak.coinroutine.coins.data.mapper.toCoinModel
import com.deepak.coinroutine.coins.domain.api.CoinsRemoteDataSource
import com.deepak.coinroutine.coins.domain.model.CoinModel
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.core.domain.map

class GetCoinsListUseCase(
    private val client: CoinsRemoteDataSource
) {

    suspend fun execute(): Result<List<CoinModel>, DataError.Remote> {
        return client.getListOfCoins().map { dto ->
            dto.data.coins.map { it.toCoinModel() }
        }
    }
}