package com.deepak.coinroutine.trade.mapper

import com.deepak.coinroutine.coins.domain.coin.Coin
import com.deepak.coinroutine.trade.presentation.common.UiTradeCoinItem

fun UiTradeCoinItem.toCoin() = Coin(
    id = id,
    name = name,
    symbol = symbol,
    iconUrl = iconUrl
)