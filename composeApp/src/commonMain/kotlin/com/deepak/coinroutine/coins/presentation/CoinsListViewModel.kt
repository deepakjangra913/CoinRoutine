package com.deepak.coinroutine.coins.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.coinroutine.coins.domain.GetCoinsListUseCase
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.core.util.formatFiat
import com.deepak.coinroutine.core.util.formatPercentage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CoinsListViewModel(
    private val getCoinsListUseCase: GetCoinsListUseCase
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
                        error = null
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
                                formattedChange = formatPercentage( coinItem.change),
                                isPositive = coinItem.change >= 0
                            )
                        }
                    )
                }
            }
        }
    }
}