package com.deepak.coinroutine.portfolio.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.core.util.formatCoinUnit
import com.deepak.coinroutine.core.util.formatFiat
import com.deepak.coinroutine.core.util.formatPercentage
import com.deepak.coinroutine.core.util.toUiText
import com.deepak.coinroutine.portfolio.domain.PortfolioCoinModel
import com.deepak.coinroutine.portfolio.domain.PortfolioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class PortfolioViewModel(
    private val portfolioRepository: PortfolioRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PortfolioState(isLoading = true))
    val state: StateFlow<PortfolioState> = combine(
        _state,
        portfolioRepository.allPortFolioCoinsFlow(),
        portfolioRepository.totalBalanceFlow(),
        portfolioRepository.cashBalanceFlow()
    ) { currentState, portfolioCoinsResponse, totalBalanceResult, cashBalance ->
        when (portfolioCoinsResponse) {
            is Result.Error -> {
                handleErrorState(currentState, portfolioCoinsResponse.error)
            }

            is Result.Success -> {
                handleSuccessState(
                    currentState = currentState,
                    portfolioCoins = portfolioCoinsResponse.data,
                    totalBalanceResult = totalBalanceResult,
                    cashBalance = cashBalance
                )
            }
        }
    }.onStart {
        portfolioRepository.initializeBalance()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = PortfolioState(isLoading = true)
    )

    private fun handleSuccessState(
        currentState: PortfolioState,
        portfolioCoins: List<PortfolioCoinModel>,
        totalBalanceResult: Result<Double, DataError>,
        cashBalance: Double
    ): PortfolioState {
        val portfolioValue = when (totalBalanceResult) {
            is Result.Error -> formatFiat(0.0)
            is Result.Success -> {
                formatFiat(totalBalanceResult.data)
            }
        }

        return currentState.copy(
            coins = portfolioCoins.map {
                it.toUiPortfolioCoinItem()
            },
            portfolioValue = portfolioValue,
            cashBalance = formatFiat(cashBalance),
            showBuyButton = portfolioCoins.isNotEmpty(),
            isLoading = false
        )
    }

    private fun handleErrorState(
        currentState: PortfolioState,
        error: DataError
    ): PortfolioState {
        return currentState.copy(
            isLoading = false,
            error = error.toUiText()
        )
    }

    private fun PortfolioCoinModel.toUiPortfolioCoinItem(): UiPortfolioCoinItem {
        return UiPortfolioCoinItem(
            id = coin.id,
            name = coin.name,
            iconUrl = coin.iconUrl,
            amountInUnitText = formatCoinUnit(ownedAmount, coin.symbol),
            amountInFiatText = formatFiat(ownedAmountInFiat),
            performancePercentText = formatPercentage(performancePercent),
            isPositive = performancePercent >= 0
        )
    }
}