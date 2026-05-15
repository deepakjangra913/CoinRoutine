package com.deepak.coinroutine.core.biometric

import androidx.compose.runtime.Composable
import com.deepak.coinroutine.biometric.IosBiometricAuthenticator

object IosPlatformContext : PlatformContext

@Composable
actual fun getPlatformContext(): PlatformContext = IosPlatformContext

actual fun getBiometricAuthenticator(context: PlatformContext): BiometricAuthenticator =
    IosBiometricAuthenticator()