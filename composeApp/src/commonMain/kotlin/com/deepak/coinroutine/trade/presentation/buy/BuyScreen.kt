package com.deepak.coinroutine.trade.presentation.buy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deepak.coinroutine.trade.presentation.common.TradeScreen
import com.deepak.coinroutine.trade.presentation.common.TradeType
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BuyScreen(
    coinId: String,
    navigateToPortfolio: () -> Unit
) {
    val viewModel = koinViewModel<BuyViewModel>(
        parameters = {
            parametersOf(coinId)
        }
    )
    val state by viewModel.state.collectAsStateWithLifecycle()

    TradeScreen(
        state = state,
        tradeType = TradeType.BUY,
        onAmountChanged = viewModel::onAmountChanged,
        onSubmitClicked = viewModel::onBuyClicked
    )
}