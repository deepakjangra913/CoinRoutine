package com.deepak.coinroutine.portfolio.domain

import com.deepak.coinroutine.coins.domain.coin.Coin

/**
 * Domain model representing a coin held in the user's portfolio.
 *
 * @property coin The core information about the coin.
 * @property performancePercent The percentage performance of the coin since purchase.
 * @property averagePurchasePrice The average price at which the coin was purchased.
 * @property ownedAmount The total amount of the coin owned.
 * @property ownedAmountInFiat The total value of the owned amount in fiat currency.
 */
data class PortfolioCoinModel(
    val coin: Coin,
    val performancePercent: Double,
    val averagePurchasePrice: Double,
    val ownedAmount: Double,
    val ownedAmountInFiat: Double
)
