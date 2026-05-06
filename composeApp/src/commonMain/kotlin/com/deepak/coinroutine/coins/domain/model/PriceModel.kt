package com.deepak.coinroutine.coins.domain.model

/**
 * Domain model representing a price point at a specific time.
 *
 * @property price The price of the asset at the given timestamp.
 * @property timeStamp The unix timestamp for this price point.
 */
data class PriceModel(
    val price: Double,
    val timeStamp: Long
)
