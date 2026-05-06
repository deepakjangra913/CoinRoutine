package com.deepak.coinroutine.core.domain

/**
 * A generic sealed interface representing the result of an operation.
 * It can be either [Success] or [Error].
 *
 * @param D The type of data returned on success.
 * @param E The type of error returned on failure.
 */
sealed interface Result<out D, out E : Error> {
    /**
     * Represents a successful operation result.
     * @property data The data returned by the operation.
     */
    data class Success<out D>(val data: D) : Result<D, Nothing>

    /**
     * Represents a failed operation result.
     * @property error The error encountered during the operation.
     */
    data class Error<out E : com.deepak.coinroutine.core.domain.Error>(val error: E) :
        Result<Nothing, E>
}

/**
 * Maps the data of a successful result using the provided [map] function.
 *
 * @param map Function to transform the success data.
 * @return A new [Result] with the transformed data or the original error.
 */
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> {
            Result.Error(error)
        }

        is Result.Success -> {
            Result.Success(map(data))
        }
    }
}

/**
 * Converts a [Result] to an [EmptyResult], discarding the data but keeping the error status.
 */
fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { Unit }
}

/**
 * Performs the given [action] if the result is [Result.Success].
 *
 * @param action The action to perform with the success data.
 * @return The original [Result].
 */
inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            this
        }

        is Result.Success -> {
            action(data)
            this
        }
    }
}

/**
 * Performs the given [action] if the result is [Result.Error].
 *
 * @param action The action to perform with the error.
 * @return The original [Result].
 */
inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action(error)
            this
        }

        is Result.Success -> this
    }
}

/**
 * Type alias for a [Result] that doesn't return data on success.
 */
typealias EmptyResult<E> = Result<Unit, E>