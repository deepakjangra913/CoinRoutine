package com.deepak.coinroutine.coins.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data transfer object for the coin price history response.
 */
@Serializable
data class CoinPriceHistoryResponseDto(
    @SerialName("data")
    val data: CoinPriceHistoryDto
)

/**
 * Data transfer object containing the history list in a price history response.
 */
@Serializable
data class CoinPriceHistoryDto(
    @SerialName("history")
    val history: List<CoinPriceDto>
)

/**
 * Data transfer object representing a single price point in time.
 *
 * @property price The price as a string (can be null).
 * @property timeStamp The unix timestamp for this price point.
 */
@Serializable
data class CoinPriceDto(
    @SerialName("price")
    val price: String?,

    @SerialName("timestamp")
    val timeStamp: Long
)