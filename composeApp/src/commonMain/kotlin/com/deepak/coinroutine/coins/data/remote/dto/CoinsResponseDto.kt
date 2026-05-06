package com.deepak.coinroutine.coins.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Data transfer object representing the response for a list of coins.
 */
@Serializable
data class CoinsResponseDto(
    val data: CoinsListDto
)

/**
 * Data transfer object containing the list of coins.
 */
@Serializable
data class CoinsListDto(
    val coins: List<CoinItemDto>
)

/**
 * Data transfer object representing an individual coin item in the list.
 */
@Serializable
data class CoinItemDto(
    val uuid: String,
    val symbol: String,
    val name: String,
    val iconUrl: String,
    val price: Double,
    val rank: Int,
    val change: String
)