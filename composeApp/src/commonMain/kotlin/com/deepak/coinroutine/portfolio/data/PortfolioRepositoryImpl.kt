package com.deepak.coinroutine.portfolio.data

import androidx.sqlite.SQLiteException
import com.deepak.coinroutine.coins.data.remote.impl.KtorCoinsRemoteDataSource
import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.EmptyResult
import com.deepak.coinroutine.core.domain.Result
import com.deepak.coinroutine.core.domain.onError
import com.deepak.coinroutine.core.domain.onSuccess
import com.deepak.coinroutine.portfolio.data.local.PortfolioDao
import com.deepak.coinroutine.portfolio.data.local.UserBalanceDao
import com.deepak.coinroutine.portfolio.data.local.UserBalanceEntity
import com.deepak.coinroutine.portfolio.data.mapper.toPortfolioCoinEntity
import com.deepak.coinroutine.portfolio.data.mapper.toPortfolioCoinModel
import com.deepak.coinroutine.portfolio.domain.PortfolioCoinModel
import com.deepak.coinroutine.portfolio.domain.PortfolioRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

/**
 * Implementation of [PortfolioRepository] that manages portfolio data using a local database
 * and remote data source.
 *
 * @property portfolioDao DAO for portfolio-related database operations.
 * @property userBalanceDao DAO for user balance-related database operations.
 * @property coinsRemoteDataSource Remote data source for fetching real-time coin prices.
 */
class PortfolioRepositoryImpl(
    private val portfolioDao: PortfolioDao,
    private val userBalanceDao: UserBalanceDao,
    private val coinsRemoteDataSource: KtorCoinsRemoteDataSource,
) : PortfolioRepository {

    /**
     * Initializes the user's cash balance with a default value if not already present in the database.
     */
    override suspend fun initializeBalance() {
        val currentBalance = userBalanceDao.getCashBalance()
        if (currentBalance == null) {
            userBalanceDao.insertBalance(
                UserBalanceEntity(
                    cashBalance = 10000.0
                )
            )
        }
    }

    /**
     * Provides a real-time flow of the user's portfolio coins, updated with current prices.
     *
     * @return A [Flow] of [Result] containing a list of [PortfolioCoinModel].
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun allPortFolioCoinsFlow(): Flow<Result<List<PortfolioCoinModel>, DataError.Remote>> {
        return portfolioDao.getAllOwnedCoins().flatMapLatest { portfolioCoinEntities ->
            if (portfolioCoinEntities.isEmpty()) {
                flow {
                    emit(Result.Success(emptyList<PortfolioCoinModel>()))
                }
            } else {
                flow {
                    coinsRemoteDataSource.getListOfCoins()
                        .onError { dataErrorRemote ->
                            emit(Result.Error(dataErrorRemote))
                        }
                        .onSuccess { coinsResponseDto ->
                            val portfolioCoins =
                                portfolioCoinEntities.mapNotNull { portfolioCoinEntity ->
                                    val coin =
                                        coinsResponseDto.data.coins.find { it.uuid == portfolioCoinEntity.coinId }
                                    coin?.let {
                                        portfolioCoinEntity.toPortfolioCoinModel(it.price)
                                    }
                                }

                            emit(Result.Success(portfolioCoins))
                        }
                }
            }
        }.catch {
            emit(Result.Error(DataError.Remote.UNKNOWN))
        }
    }

    /**
     * Retrieves a specific portfolio coin by its ID, with its current price.
     *
     * @param coinId The unique identifier of the coin.
     * @return A [Result] containing the [PortfolioCoinModel] or null if not in portfolio.
     */
    override suspend fun getPortfolioCoin(coinId: String): Result<PortfolioCoinModel?, DataError.Remote> {
        coinsRemoteDataSource.getCoinById(coinId)
            .onError { error ->
                return Result.Error(error)
            }
            .onSuccess { coinDto ->
                val portfolioCoinEntity = portfolioDao.getCoinById(coinId)
                return if (portfolioCoinEntity != null) {
                    Result.Success(portfolioCoinEntity.toPortfolioCoinModel(coinDto.data.coin.price))
                } else {
                    Result.Success(null)
                }
            }

        return Result.Error(DataError.Remote.UNKNOWN)
    }

    /**
     * Saves a coin to the local portfolio database.
     *
     * @param portfolioCoinModel The coin model to save.
     * @return [Result.Success] on success, or [DataError.Local.DISK_FULL] on error.
     */
    override suspend fun savePortfolioCoin(portfolioCoinModel: PortfolioCoinModel): EmptyResult<DataError.Local> {
        try {
            portfolioDao.insert(portfolioCoinModel.toPortfolioCoinEntity())
            return Result.Success(Unit)
        } catch (e: SQLiteException) {
            return Result.Error(DataError.Local.DISK_FULL)
        }
    }

    /**
     * Deletes a coin from the local portfolio database.
     *
     * @param coinId The unique identifier of the coin to remove.
     */
    override suspend fun removeCoinFromPortfolio(coinId: String) {
        portfolioDao.deletePortfolioItem(coinId)
    }

    /**
     * Calculates the total current value of all coins in the portfolio.
     *
     * @return A [Flow] of [Result] containing the total portfolio value in fiat.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun calculateTotalPortfolioValue(): Flow<Result<Double, DataError.Remote>> {
        return portfolioDao.getAllOwnedCoins().flatMapLatest { portfolioCoinEntities ->
            if (portfolioCoinEntities.isEmpty()) {
                flow {
                    emit(Result.Success(0.0))
                }
            } else {
                flow {
                    val apiResult = coinsRemoteDataSource.getListOfCoins()
                    apiResult.onError { error ->
                        emit(Result.Error(error))
                    }.onSuccess { coinsResponseDto ->
                        val totalValue = portfolioCoinEntities.sumOf { ownedCoin ->
                            val coinPrice =
                                coinsResponseDto.data.coins.find { it.uuid == ownedCoin.coinId }?.price
                                    ?: 0.0
                            ownedCoin.amountOwned * coinPrice
                        }
                        emit(Result.Success(totalValue))
                    }
                }
            }
        }.catch {
            emit(Result.Error(DataError.Remote.UNKNOWN))
        }
    }

    /**
     * Provides a flow of the user's cash balance.
     *
     * @return A [Flow] of the cash balance.
     */
    override fun cashBalanceFlow(): Flow<Double> {
        return flow {
            emit(userBalanceDao.getCashBalance() ?: 10000.0)
        }
    }

    /**
     * Provides a flow of the total user balance (cash + portfolio value).
     *
     * @return A [Flow] of [Result] containing the total balance.
     */
    override fun totalBalanceFlow(): Flow<Result<Double, DataError.Remote>> {
        return combine(
            cashBalanceFlow(),
            calculateTotalPortfolioValue()
        ) { cashBalance, portfolioResult ->
            when (portfolioResult) {
                is Result.Error -> {
                    Result.Error(portfolioResult.error)
                }

                is Result.Success -> {
                    Result.Success(cashBalance + portfolioResult.data)
                }
            }
        }
    }

    /**
     * Updates the user's cash balance in the local database.
     *
     * @param newBalance The updated cash balance.
     */
    override suspend fun updateCashBalance(newBalance: Double) {
        userBalanceDao.updateCashBalance(newBalance)
    }
}