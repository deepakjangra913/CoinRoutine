package com.deepak.coinroutine.trade.domain

import com.deepak.coinroutine.coins.domain.coin.Coin
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.EmptyResult
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.portfolio.domain.PortfolioRepository
import kotlinx.coroutines.flow.first

class SellCoinUseCase(
    private val portfolioRepository: PortfolioRepository
) {

    suspend operator fun invoke(
        coin: Coin,
        amountInFiat: Double,
        price: Double
    ): EmptyResult<DataError> {

        val sellAllThreshold = 1
        when (val existingCoinResponse = portfolioRepository.getPortfolioCoin(coinId = coin.id)) {
            is Result.Error -> {
                return existingCoinResponse
            }

            is Result.Success -> {
                val existingCoin = existingCoinResponse.data
                val sellAmountInUnit = amountInFiat / price

                val balance = portfolioRepository.cashBalanceFlow().first()
                if (existingCoin == null || existingCoin.ownedAmount < sellAmountInUnit) {
                    return Result.Error(DataError.Local.INSUFFICIENT_FUNDS)
                }

                val remainingAmountInFiat = existingCoin.ownedAmountInFiat - amountInFiat
                val remainingAmountInUnit = existingCoin.ownedAmount - sellAmountInUnit

                if (remainingAmountInFiat < sellAllThreshold) {
                    portfolioRepository.removeCoinFromPortfolio(coinId = coin.id)
                } else {
                    portfolioRepository.savePortfolioCoin(
                        existingCoin.copy(
                            ownedAmount = remainingAmountInUnit,
                            ownedAmountInFiat = remainingAmountInFiat
                        )
                    )
                }

                portfolioRepository.updateCashBalance(balance + amountInFiat)
                return Result.Success(Unit)
            }
        }
    }
}