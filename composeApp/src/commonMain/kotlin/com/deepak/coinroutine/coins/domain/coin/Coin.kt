package com.deepak.coinroutine.coins.domain.coin

/**
 * Core domain model for a cryptocurrency.
 *
 * @property id Unique identifier for the coin.
 * @property name Display name of the coin (e.g., "Bitcoin").
 * @property symbol Ticker symbol of the coin (e.g., "BTC").
 * @property iconUrl URL to the coin's icon image.
 */
data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val iconUrl: String
)
