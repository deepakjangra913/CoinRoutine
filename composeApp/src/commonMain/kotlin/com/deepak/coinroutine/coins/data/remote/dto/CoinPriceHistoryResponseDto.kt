package com.deepak.coinroutine.coins.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceHistoryResponseDto(
    @SerialName("data")
    val data: CoinPriceHistoryDto
)

@Serializable
data class CoinPriceHistoryDto(
    @SerialName("history")
    val history: List<CoinPriceDto>
)

@Serializable
data class CoinPriceDto(
    @SerialName("price")
    val price: String?,

    @SerialName("timestamp")
    val timeStamp: Long
)