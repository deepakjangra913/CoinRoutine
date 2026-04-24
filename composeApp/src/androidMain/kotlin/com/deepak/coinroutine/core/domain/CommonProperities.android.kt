package com.deepak.coinroutine.core.domain

import com.deepak.coinroutine.BuildConfig

actual fun getApiToken(): String  = BuildConfig.API_TOKEN