package com.deepak.coinroutine

import androidx.compose.ui.window.ComposeUIViewController
import com.deepak.coinroutine.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }