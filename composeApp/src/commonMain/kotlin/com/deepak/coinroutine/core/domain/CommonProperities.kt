package com.deepak.coinroutine.core.domain

/**
 * Retrieves the API token required for making authorized network requests.
 * Implementation is platform-specific.
 *
 * @return The API token as a [String].
 */
expect fun getApiToken(): String