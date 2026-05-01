package com.deepak.coinroutine.portfolio.data.mapper

import com.deepak.coinroutine.coins.domain.coin.Coin
import com.deepak.coinroutine.portfolio.data.local.PortfolioCoinEntity
import com.deepak.coinroutine.portfolio.domain.PortfolioCoinModel
import kotlinx.datetime.Clock

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