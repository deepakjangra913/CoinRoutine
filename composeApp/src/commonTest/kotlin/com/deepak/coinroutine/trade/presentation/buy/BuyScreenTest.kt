package com.deepak.coinroutine.trade.presentation.buy

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.deepak.coinroutine.trade.presentation.common.TradeScreen
import com.deepak.coinroutine.trade.presentation.common.TradeState
import com.deepak.coinroutine.trade.presentation.common.TradeType
import com.deepak.coinroutine.trade.presentation.common.UiTradeCoinItem
import kotlin.test.Test

class BuyScreenTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun checkSubmitButtonLabelChangesWithTradeType() = runComposeUiTest {
        val state = TradeState(
            coin = UiTradeCoinItem(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                iconUrl = "url",
                price = 50000.0
            )
        )

        setContent {
            TradeScreen(
                state = state,
                tradeType = TradeType.BUY,
                onAmountChanged = {},
                onSubmitClicked = {}
            )
        }
        onNodeWithText("Sell Now").assertDoesNotExist()
        onNodeWithText("Buy Now").assertExists()
        onNodeWithText("Buy Now").assertIsDisplayed()

        setContent {
            TradeScreen(
                state = state,
                tradeType = TradeType.SELL,
                onAmountChanged = {},
                onSubmitClicked = {}
            )
        }

        onNodeWithText("Buy Now").assertDoesNotExist()
        onNodeWithText("Sell Now").assertExists()
        onNodeWithText("Sell Now").assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun checkIfCoinNameShowProperlyInBuy() = runComposeUiTest {
        val state = TradeState(
            coin = UiTradeCoinItem(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                iconUrl = "url",
                price = 50000.0
            )
        )

        setContent {
            TradeScreen(
                state = state,
                tradeType = TradeType.BUY,
                onAmountChanged = {},
                onSubmitClicked = {}
            )
        }

        onNodeWithTag("trade_screen_coin_name").assertExists()
        onNodeWithTag("trade_screen_coin_name").assertTextEquals("Bitcoin")
    }
}