package com.deepak.coinroutine.portfolio.data.mapper

import com.deepak.coinroutine.coins.domain.coin.Coin
import com.deepak.coinroutine.portfolio.data.local.PortfolioCoinEntity
import com.deepak.coinroutine.portfolio.domain.PortfolioCoinModel
import kotlinx.datetime.Clock

/**
 * Maps a [PortfolioCoinEntity] to a [PortfolioCoinModel].
 * Calculates the performance percentage and fiat value based on the [currentPrice].
 *
 * @param currentPrice The current market price of the coin.
 * @return A [PortfolioCoinModel] populated with current data.
 */
fun PortfolioCoinEntity.toPortfolioCoinModel(
    currentPrice: Double,
): PortfolioCoinModel {
    return PortfolioCoinModel(
        coin = Coin(
            id = coinId,
            name = name,
            symbol = symbol,
            iconUrl = iconUrl
        ),
        performancePercent = ((currentPrice - averagePurchasePrice) / averagePurchasePrice) * 100,
        averagePurchasePrice = averagePurchasePrice,
        ownedAmount = amountOwned,
        ownedAmountInFiat = amountOwned * currentPrice
    )
}

/**
 * Maps a [PortfolioCoinModel] to a [PortfolioCoinEntity] for database storage.
 * Records the current timestamp as the update time.
 *
 * @return A [PortfolioCoinEntity] ready for persistence.
 */
fun PortfolioCoinModel.toPortfolioCoinEntity(): PortfolioCoinEntity {
    return PortfolioCoinEntity(
        coinId = coin.id,
        name = coin.name,
        symbol = coin.symbol,
        iconUrl = coin.iconUrl,
        averagePurchasePrice = averagePurchasePrice,
        amountOwned = ownedAmount,
        timestamp = Clock.System.now().toEpochMilliseconds(),
    )
}