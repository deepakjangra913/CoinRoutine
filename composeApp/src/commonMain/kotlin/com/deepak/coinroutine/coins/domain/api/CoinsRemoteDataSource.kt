package com.deepak.coinroutine.coins.domain.api

import com.deepak.coinroutine.coins.data.remote.dto.CoinDetailsResponseDto
import com.deepak.coinroutine.coins.data.remote.dto.CoinPriceHistoryResponseDto
import com.deepak.coinroutine.coins.data.remote.dto.CoinsResponseDto
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.Result

interface CoinsRemoteDataSource {

    suspend fun getListOfCoins(): Result<CoinsResponseDto, DataError.Remote>

    suspend fun getPriceHistory(coinId: String): Result<CoinPriceHistoryResponseDto, DataError.Remote>

    suspend fun getCoinById(coinId: String): Result<CoinDetailsResponseDto, DataError.Remote>

}