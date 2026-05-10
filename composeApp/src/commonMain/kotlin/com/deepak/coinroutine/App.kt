package com.deepak.coinroutine

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.deepak.coinroutine.coins.presentation.CoinsListScreen
import com.deepak.coinroutine.core.navigation.Buy
import com.deepak.coinroutine.core.navigation.Coins
import com.deepak.coinroutine.core.navigation.Portfolio
import com.deepak.coinroutine.core.navigation.Sell
import com.deepak.coinroutine.portfolio.presentation.PortfolioScreen
import com.deepak.coinroutine.theme.CoinRoutineTheme
import com.deepak.coinroutine.trade.presentation.buy.BuyScreen
import com.deepak.coinroutine.trade.presentation.sell.SellScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * The main entry point for the Compose Multiplatform application.
 * Sets up the theme and the root navigation/screen structure.
 */
@Composable
@Preview
fun App() {
    val navController: NavHostController = rememberNavController()
    CoinRoutineTheme {
        NavHost(
            navController = navController,
            startDestination = Portfolio,
            modifier = Modifier.fillMaxSize()
        ) {
            composable<Portfolio> {
                PortfolioScreen(
                    onCoinItemClicked = { coinId ->
                        navController.navigate(Sell(coinId = coinId))
                    },
                    onDiscoverCoinsClicked = {
                        navController.navigate(Coins)
                    }
                )
            }

            composable<Coins> {
                CoinsListScreen(
                    onCoinClicked = { coinId ->
                        navController.navigate(Buy(coinId = coinId))
                    }
                )
            }

            composable<Buy> { navBackStackEntry ->
                val coinId: String = navBackStackEntry.toRoute<Buy>().coinId
                BuyScreen(
                    coinId = coinId,
                    navigateToPortfolio = {
                        navController.navigate(Portfolio) {
                            popUpTo(Portfolio) { inclusive = true }
                        }
                    }
                )
            }

            composable<Sell> { navBackStackEntry ->
                val coinId: String = navBackStackEntry.toRoute<Sell>().coinId
                SellScreen(
                    coinId = coinId,
                    navigateToPortfolio = {
                        navController.navigate(Portfolio) {
                            popUpTo(Portfolio) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}