package com.deepak.coinroutine.portfolio.domain

import com.deepak.coinroutine.coins.domain.coin.Coin

data class PortfolioCoinModel(
    val coin: Coin,
    val performancePercent: Double,
    val averagePurchasePrice: Double,
    val ownedAmount: Double,
    val ownedAmountInFiat: Double
)
