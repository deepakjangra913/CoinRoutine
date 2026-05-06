package com.deepak.coinroutine.portfolio.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing the user's cash balance.
 *
 * @property id Unique identifier for the balance record (usually a single record with ID 1).
 * @property cashBalance The current amount of cash the user has available for trading.
 */
@Entity
data class UserBalanceEntity(
    @PrimaryKey val id: Int = 1,
    val cashBalance: Double
)
