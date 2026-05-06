package com.deepak.coinroutine.core.domain

/**
 * Represents errors that can occur during data operations.
 */
sealed interface DataError : Error {
    /**
     * Errors related to remote data fetching (e.g., API calls).
     */
    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }

    /**
     * Errors related to local data operations (e.g., database, file system).
     */
    enum class Local: DataError {
        DISK_FULL,
        INSUFFICIENT_FUNDS,
        UNKNOWN
    }
}