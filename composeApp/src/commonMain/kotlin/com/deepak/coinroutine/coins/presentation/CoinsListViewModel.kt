package com.deepak.coinroutine.coins.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.coinroutine.coins.domain.GetCoinPriceHistoryUseCase
import com.deepak.coinroutine.coins.domain.GetCoinsListUseCase
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.core.util.formatFiat
import com.deepak.coinroutine.core.util.formatPercentage
import com.deepak.coinroutine.core.util.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinsListViewModel(
    private val getCoinsListUseCase: GetCoinsListUseCase,
    private val getCoinsPriceHistoryUseCase: GetCoinPriceHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CoinsState())
    val state = _state
        .onStart {
            getAllCoins()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CoinsState()
        )

    private suspend fun getAllCoins() {
        when (val coinsResponse = getCoinsListUseCase.execute()) {
            is Result.Error -> {
                _state.update {
                    it.copy(
                        coins = emptyList(),
                        error = coinsResponse.error.toUiText()
                    )
                }
            }

            is Result.Success -> {
                _state.update {
                    CoinsState(
                        coins = coinsResponse.data.map { coinItem ->
                            UiCoinListItem(
                                id = coinItem.coin.id,
                                name = coinItem.coin.name,
                                symbol = coinItem.coin.symbol,
                                iconUrl = coinItem.coin.iconUrl,
                                formattedPrice = formatFiat(coinItem.price),
                                formattedChange = formatPercentage(coinItem.change),
                                isPositive = coinItem.change >= 0
                            )
                        }
                    )
                }
            }
        }
    }

    fun onCoinLongPress(coinId: String) {
        _state.update {
            it.copy(
                uiChartState = UiChartState(
                    sparkLine = emptyList(),
                    isLoading = true
                )
            )
        }

        viewModelScope.launch {
            when (val priceHistory = getCoinsPriceHistoryUseCase.execute(coinId)) {
                is Result.Success -> {
                    _state.update { currentState ->
                        currentState.copy(
                            uiChartState = UiChartState(
                                sparkLine = priceHistory.data.sortedBy { it.timeStamp }
                                    .map { it.price },
                                isLoading = false,
                                coinName = _state.value.coins.find { it.id == coinId }?.name.orEmpty(),
                            )
                        )
                    }
                }

                is Result.Error -> {
                    _state.update { currentState ->
                        currentState.copy(
                            uiChartState = UiChartState(
                                sparkLine = emptyList(),
                                isLoading = false,
                                coinName = ""
                            )
                        )
                    }
                }
            }
        }
    }
}