package com.deepak.coinroutine.biometric

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import coinroutine.composeapp.generated.resources.Res
import com.deepak.coinroutine.di.platformModule
import org.jetbrains.compose.resources.vectorResource

val BiometricIcon: ImageVector
    @Composable
    get() = when (platformModule) {
        is Platform.Android -> vectorResource(Res.drawable.ic_fingerprint)
        is Platform.Ios -> vectorResource(Res.drawable.ic_face_id)
    }