package com.deepak.coinroutine.trade.presentation.buy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.coinroutine.coins.domain.GetCoinDetailsUseCase
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.core.util.formatFiat
import com.deepak.coinroutine.core.util.toUiText
import com.deepak.coinroutine.portfolio.domain.PortfolioRepository
import com.deepak.coinroutine.trade.domain.BuyCoinUseCase
import com.deepak.coinroutine.trade.mapper.toCoin
import com.deepak.coinroutine.trade.presentation.common.TradeState
import com.deepak.coinroutine.trade.presentation.common.UiTradeCoinItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BuyViewModel(
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    private val portfolioRepository: PortfolioRepository,
    private val buyCoinUseCase: BuyCoinUseCase
) : ViewModel() {

    private val tempCoinId = "1" //TODO: will replace by nav args
    private val _amount = MutableStateFlow("")
    private val _state = MutableStateFlow(TradeState())
    val state = combine(
        _state, _amount
    ) { state, amount ->
        state.copy(amount = amount)
    }.onStart {
        val balance = portfolioRepository.cashBalanceFlow().first()
        getCoinDetails(balance)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TradeState(isLoading = true)
    )


    private suspend fun getCoinDetails(balance: Double) {
        when (val coinResponse = getCoinDetailsUseCase.execute(tempCoinId)) {
            is Result.Success -> {
                _state.update {
                    it.copy(
                        coin = UiTradeCoinItem(
                            id = coinResponse.data.coin.id,
                            name = coinResponse.data.coin.name,
                            symbol = coinResponse.data.coin.symbol,
                            iconUrl = coinResponse.data.coin.iconUrl,
                            price = coinResponse.data.price
                        ),
                        availableAmount = "Available: ${formatFiat(balance)}"
                    )
                }
            }

            is Result.Error -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = coinResponse.error.toUiText()
                    )
                }
            }
        }
    }

    fun onAmountChanged(amount: String) {
        _amount.value = amount
    }

    fun onBuyClicked() {
        val tradeCoin = state.value.coin ?: return
        viewModelScope.launch {
            val buyCoinResponse = buyCoinUseCase.invoke(
                coin = tradeCoin.toCoin(),
                amountInFiat = _amount.value.toDouble(),
                price = tradeCoin.price
            )

            when (buyCoinResponse) {
                is Result.Success -> {
                    // TODO (Navigate to Listing Screen)
                }

                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = buyCoinResponse.error.toUiText()
                        )
                    }
                }
            }
        }
    }
}