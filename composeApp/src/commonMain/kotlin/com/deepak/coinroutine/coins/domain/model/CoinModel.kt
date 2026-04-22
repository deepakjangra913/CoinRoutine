package com.deepak.coinroutine.coins.domain.model

import com.deepak.coinroutine.coins.domain.coin.Coin

data class CoinModel(
    val coin: Coin,
    val price: Double,
    val change: Double
)