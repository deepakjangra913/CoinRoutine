package com.deepak.coinroutine.coins.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.deepak.coinroutine.coins.presentation.component.CoinChartDialog
import com.deepak.coinroutine.theme.LocalCoinRoutineColorsPalette
import org.koin.compose.viewmodel.koinViewModel

/**
 * Screen that displays a list of cryptocurrencies.
 *
 * @param onCoinClicked Callback invoked when a coin item is clicked.
 */
@Composable
fun CoinsListScreen(
    onCoinClicked: (String) -> Unit
) {

    val coinsListViewModel = koinViewModel<CoinsListViewModel>()
    val state by coinsListViewModel.state.collectAsStateWithLifecycle()

    CoinsListContent(
        state = state,
        onDismissChart = {
            coinsListViewModel.onDismissChart()
        },
        onCoinLongPressed = { coinId ->
            coinsListViewModel.onCoinLongPress(coinId)
        },
        onCoinClicked = onCoinClicked
    )
}

/**
 * Main content of the Coins List screen.
 *
 * @param state Current UI state.
 * @param onDismissChart Callback to dismiss the coin chart dialog.
 * @param onCoinLongPressed Callback for long pressing a coin item.
 * @param onCoinClicked Callback for clicking a coin item.
 */
@Composable
fun CoinsListContent(
    state: CoinsState,
    onDismissChart: () -> Unit,
    onCoinLongPressed: (String) -> Unit,
    onCoinClicked: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (state.uiChartState != null) {
            CoinChartDialog(
                uiChartState = state.uiChartState,
                onDismiss = onDismissChart
            )
        }

        CoinsList(
            state.coins,
            onCoinLongPressed = onCoinLongPressed,
            onCoinClicked = onCoinClicked
        )
    }
}

/**
 * Displays a lazy list of coins.
 *
 * @param coins List of coins to display.
 * @param onCoinLongPressed Callback for long pressing a coin item.
 * @param onCoinClicked Callback for clicking a coin item.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CoinsList(
    coins: List<UiCoinListItem>,
    onCoinLongPressed: (String) -> Unit,
    onCoinClicked: (String) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.background(
            MaterialTheme.colorScheme.background
        )
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            stickyHeader {
                Text(
                    text = "🔥 Top Coins",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                )
            }
            items(items = coins, key = { it.id }) { item ->
                CoinListItem(
                    coin = item,
                    onCoinClicked = onCoinClicked,
                    onCoinLongPressed = onCoinLongPressed
                )
            }
        }
    }
}

/**
 * Individual coin list item component.
 *
 * @param coin UI model for the coin.
 * @param onCoinLongPressed Callback for long pressing the item.
 * @param onCoinClicked Callback for clicking the item.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CoinListItem(
    coin: UiCoinListItem,
    onCoinLongPressed: (String) -> Unit,
    onCoinClicked: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = {
                    onCoinLongPressed(coin.id)
                },
                onClick = {
                    onCoinClicked(coin.id)
                }
            )
            .padding(16.dp)
    ) {

        AsyncImage(
            model = coin.iconUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(4.dp)
                .clip(CircleShape)
                .size(40.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = coin.name,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = coin.symbol,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = coin.formattedPrice,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = coin.formattedChange,
                color = if (coin.isPositive) LocalCoinRoutineColorsPalette.current.profitGreen else LocalCoinRoutineColorsPalette.current.lossRed,
                fontSize = MaterialTheme.typography.titleSmall.fontSize
            )
        }
    }
}
