package com.deepak.coinroutine.core.network

import com.deepak.coinroutine.core.domain.DataError
import com.deepak.coinroutine.core.domain.Result
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

/**
 * Safely executes a network request and wraps the result in a [Result].
 * Handles common network exceptions and maps them to [DataError.Remote].
 *
 * @param T The type of the response body.
 * @param execute A lambda that performs the network request and returns an [HttpResponse].
 * @return A [Result] containing the deserialized body of type [T] or a [DataError.Remote].
 */
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Remote> {

    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        return Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        return Result.Error(DataError.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

/**
 * Maps an [HttpResponse] to a [Result], handling different HTTP status codes.
 *
 * @param T The type of the response body.
 * @param response The [HttpResponse] received from the network.
 * @return A [Result] containing the deserialized body of type [T] or a [DataError.Remote].
 */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: Exception) {
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        }
        400 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Remote.SERVER)
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}