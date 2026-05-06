package com.deepak.coinroutine.trade.domain

import com.deepak.coinroutine.coins.domain.coin.Coin
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.EmptyResult
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.portfolio.domain.PortfolioCoinModel
import com.deepak.coinroutine.portfolio.domain.PortfolioRepository
import kotlinx.coroutines.flow.first

/**
 * Use case for purchasing a cryptocurrency and adding it to the user's portfolio.
 *
 * @property portfolioRepository The repository to manage portfolio data and balance.
 */
class BuyCoinUseCase(
    private val portfolioRepository: PortfolioRepository
) {

    /**
     * Executes the buy transaction.
     *
     * @param coin The coin to be purchased.
     * @param amountInFiat The amount of the coin to buy, specified in fiat currency.
     * @param price The current market price of the coin.
     * @return An [EmptyResult] indicating success or [DataError] on failure (e.g., insufficient funds).
     */
    suspend operator fun invoke(
        coin: Coin,
        amountInFiat: Double,
        price: Double
    ): EmptyResult<DataError> {
        val balance = portfolioRepository.cashBalanceFlow().first()
        if (balance < amountInFiat) {
            return Result.Error(DataError.Local.INSUFFICIENT_FUNDS)
        }

        val existingCoinResult = portfolioRepository.getPortfolioCoin(coin.id)
        val existingCoin = when (existingCoinResult) {
            is Result.Error -> return Result.Error(existingCoinResult.error)
            is Result.Success -> existingCoinResult.data
        }

        val amountInUnit = amountInFiat / price
        if (existingCoin != null) {
            val newAmountOwned = existingCoin.ownedAmountInFiat + amountInUnit
            val newTotalInvestment = existingCoin.ownedAmountInFiat + amountInFiat

            val newAveragePrice = newTotalInvestment / newAmountOwned
            portfolioRepository.savePortfolioCoin(
                existingCoin.copy(
                    ownedAmount = newAmountOwned,
                    ownedAmountInFiat = newTotalInvestment,
                    averagePurchasePrice = newAveragePrice
                )
            )
        } else {
            portfolioRepository.savePortfolioCoin(
                PortfolioCoinModel(
                    coin = coin,
                    performancePercent = 0.0,
                    averagePurchasePrice = price,
                    ownedAmountInFiat = amountInFiat,
                    ownedAmount = amountInUnit
                )
            )
        }

        portfolioRepository.updateCashBalance(balance - amountInFiat)
        return Result.Success(Unit)
    }
}