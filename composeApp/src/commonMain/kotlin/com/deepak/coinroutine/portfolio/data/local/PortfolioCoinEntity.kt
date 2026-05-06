package com.deepak.coinroutine.portfolio.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing a coin stored in the local portfolio database.
 *
 * @property coinId Unique identifier for the coin (primary key).
 * @property name Name of the cryptocurrency.
 * @property symbol Ticker symbol of the cryptocurrency.
 * @property iconUrl URL of the coin's icon image.
 * @property averagePurchasePrice The average price at which the user bought the coin.
 * @property amountOwned Total units of the coin owned by the user.
 * @property timestamp The time when the record was last updated.
 */
@Entity
class PortfolioCoinEntity(
    @PrimaryKey val coinId: String,
    val name: String,
    val symbol: String,
    val iconUrl: String,
    val averagePurchasePrice: Double,
    val amountOwned: Double,
    val timestamp: Long
)
