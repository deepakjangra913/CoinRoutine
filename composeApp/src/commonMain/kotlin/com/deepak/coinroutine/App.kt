package com.deepak.coinroutine

import androidx.compose.runtime.Composable
import com.deepak.coinroutine.coins.presentation.CoinsListScreen
import com.deepak.coinroutine.theme.CoinRoutineTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    CoinRoutineTheme {
        CoinsListScreen {}
    }
}