package com.deepak.coinroutine.trade.presentation.sell

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deepak.coinroutine.trade.presentation.common.TradeScreen
import com.deepak.coinroutine.trade.presentation.common.TradeType
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SellScreen(
    coinId: String,
    navigateToPortfolio: () -> Unit
) {
    val viewModel = koinViewModel<SellViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    TradeScreen(
        state = state,
        tradeType = TradeType.SELL,
        onAmountChanged = viewModel::onAmountChanged,
        onSubmitClicked = viewModel::onSellClicked
    )
}