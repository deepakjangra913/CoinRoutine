package com.deepak.coinroutine.coins.data.mapper

import com.deepak.coinroutine.coins.data.remote.dto.CoinItemDto
import com.deepak.coinroutine.coins.data.remote.dto.CoinPriceDto
import com.deepak.coinroutine.coins.domain.coin.Coin
import com.deepak.coinroutine.coins.domain.model.CoinModel
import com.deepak.coinroutine.coins.domain.model.PriceModel

fun CoinItemDto.toCoinModel() = CoinModel(
    coin = Coin(
        id = uuid,
        name = name,
        symbol = symbol,
        iconUrl = iconUrl,
    ),
    price = price.toDoubleOrNull() ?: 0.0,
    change = change.toDoubleOrNull() ?: 0.0
)

fun CoinPriceDto.toPriceModel() = PriceModel(
    price = price ?: 0.0,
    timeStamp = timeStamp
)