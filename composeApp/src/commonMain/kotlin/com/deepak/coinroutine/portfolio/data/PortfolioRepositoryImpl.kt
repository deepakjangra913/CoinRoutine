package com.deepak.coinroutine.portfolio.data

import com.deepak.coinroutine.coins.data.remote.impl.KtorCoinsRemoteDataSource
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.EmptyResult
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.portfolio.data.local.PortfolioDao
import com.deepak.coinroutine.portfolio.data.local.UserBalanceDao
import com.deepak.coinroutine.portfolio.data.local.UserBalanceEntity
import com.deepak.coinroutine.portfolio.domain.PortfolioCoinModel
import com.deepak.coinroutine.portfolio.domain.PortfolioRepository
import kotlinx.coroutines.flow.Flow

class PortfolioRepositoryImpl(
    private val portfolioDao: PortfolioDao,
    private val userBalanceDao: UserBalanceDao,
    private val coinsRemoteDataSource: KtorCoinsRemoteDataSource,
) : PortfolioRepository {

    override suspend fun initializeBalance() {
        val currentBalance = userBalanceDao.getCashBalance()
        if (currentBalance == null){
            userBalanceDao.insertBalance(
                UserBalanceEntity(
                    cashBalance = 10000.0
                )
            )
        }
    }

    override fun allPortFolioCoinsFlow(): Flow<Result<List<PortfolioCoinModel>, DataError.Remote>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPortfolioCoin(coinId: String): Result<PortfolioCoinModel?, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun savePortfolioCoin(portfolioCoinModel: PortfolioCoinModel): EmptyResult<DataError.Local> {
        TODO("Not yet implemented")
    }

    override suspend fun removeCoinFromPortfolio(coinId: String) {
        TODO("Not yet implemented")
    }

    override fun calculateTotalPortfolioValue(): Flow<Result<Double, DataError.Remote>> {
        TODO("Not yet implemented")
    }

    override fun totalBalanceFlow(): Flow<Result<Double, DataError.Remote>> {
        TODO("Not yet implemented")
    }

    override fun cashBalanceFlow(): Flow<Double> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCashBalance(newBalance: Double) {
        TODO("Not yet implemented")
    }
}