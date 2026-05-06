package com.deepak.coinroutine.coins.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Data transfer object for the coin details response.
 */
@Serializable
data class CoinDetailsResponseDto(
    val data: CoinResponseDto
)

/**
 * Data transfer object containing the coin data in a details response.
 */
@Serializable
data class CoinResponseDto(
    val coin: CoinItemDto
)