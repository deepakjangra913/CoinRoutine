package com.deepak.coinroutine.coins.domain.model

import com.deepak.coinroutine.coins.domain.coin.Coin

/**
 * Domain model representing a cryptocurrency with its current price and 24h change.
 *
 * @property coin The core information about the coin (id, name, symbol, etc.).
 * @property price The current price of the coin in fiat currency.
 * @property change The percentage change in price over the last 24 hours.
 */
data class CoinModel(
    val coin: Coin,
    val price: Double,
    val change: Double
)
