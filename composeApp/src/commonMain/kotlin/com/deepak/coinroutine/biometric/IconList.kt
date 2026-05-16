package com.deepak.coinroutine.biometric

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import coinroutine.composeapp.generated.resources.Res
import coinroutine.composeapp.generated.resources.ic_face_id
import coinroutine.composeapp.generated.resources.ic_fingerprint
import com.deepak.coinroutine.Platform
import com.deepak.coinroutine.platform
import org.jetbrains.compose.resources.vectorResource

val BiometricIcon: ImageVector
    @Composable
    get() = when (platform) {
        is Platform.Android -> vectorResource(Res.drawable.ic_fingerprint)
        is Platform.Ios -> vectorResource(Res.drawable.ic_face_id)
    }