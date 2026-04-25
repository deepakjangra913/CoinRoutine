package com.deepak.coinroutine

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.deepak.coinroutine.coins.presentation.CoinsListScreen
import com.deepak.coinroutine.theme.CoinRoutineTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    CoinRoutineTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            CoinsListScreen {}
        }
    }
}