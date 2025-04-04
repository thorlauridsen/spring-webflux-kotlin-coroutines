package com.github.thorlauridsen.service

import com.github.thorlauridsen.model.Flight
import com.github.thorlauridsen.model.Hotel
import com.github.thorlauridsen.model.RentalCar
import com.github.thorlauridsen.config.GatewayConfig
import com.github.thorlauridsen.model.TravelDetails
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.time.Duration
import java.time.OffsetDateTime

/**
 * Service class for fetching travel details.
 * This is an example of how to utilize Kotlin Coroutines
 * to optimize performance when executing multiple remote requests.
 *
 * This service fetches data from three different endpoints:
 * - /hotels
 * - /flights
 * - /rentalcars
 *
 * The data is fetched asynchronously using coroutines and synchronously without coroutines.
 * Both functions will measure the time it takes to fetch the data.
 *
 * The provider subproject exposes endpoints for hotels, flights, and rental cars.
 * The endpoints have simulated delays to demonstrate the benefits of using coroutines.
 *
 * @param client [WebClient] for making HTTP requests.
 * @param gatewayConfig [GatewayConfig] configuration for the target URL.
 */
@Service
class TravelService(
    private val client: WebClient,
    private val gatewayConfig: GatewayConfig,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Fetch data from the target URL.
     * @param uri The URI to fetch data from.
     * @return A list of objects of type T.
     */
    private suspend inline fun <reified T> fetch(uri: String): List<T> {
        logger.info("Executing request HTTP GET $uri")
        return client.get()
            .uri(uri)
            .retrieve()
            .awaitBody()
    }

    /**
     * Get travel details asynchronously.
     *
     * This will initiate three asynchronous requests
     * to fetch data from the three different endpoints.
     * The requests will be executed concurrently using coroutines.
     *
     * Each async block will fetch data from one of the endpoints.
     * Then we use .await() on each deferred object to get the result.
     *
     * @return [TravelDetails] containing the fetched data.
     */
    private suspend fun getDetailsAsync(): TravelDetails {
        return coroutineScope {
            val hotelsDeferred = async { fetch<Hotel>("/hotels") }
            val flightsDeferred = async { fetch<Flight>("/flights") }
            val rentalCarsDeferred = async { fetch<RentalCar>("/rentalcars") }

            return@coroutineScope TravelDetails(
                hotels = hotelsDeferred.await(),
                flights = flightsDeferred.await(),
                rentalCars = rentalCarsDeferred.await()
            )
        }
    }

    /**
     * Get travel details synchronously.
     *
     * Since we are not using coroutines here, the requests will be executed sequentially.
     * This means we will wait for the response from one endpoint before fetching the next.
     *
     * @return [TravelDetails] containing the fetched data.
     */
    private suspend fun getDetailsSync(): TravelDetails {
        val hotels = fetch<Hotel>("/hotels")
        val flights = fetch<Flight>("/flights")
        val rentalCars = fetch<RentalCar>("/rentalcars")

        return TravelDetails(
            hotels = hotels,
            flights = flights,
            rentalCars = rentalCars
        )
    }

    /**
     * Helper function to measure execution time of a suspend function.
     * @param suspendFunction The suspend function to execute and measure.
     * @return The result of the function execution.
     */
    private suspend fun <T> measureExecutionTime(
        suspendFunction: suspend () -> T,
    ): T {
        val start = OffsetDateTime.now()
        val result = suspendFunction()

        val duration = Duration.between(start, OffsetDateTime.now())
        logger.info("Fetched travel details in ${duration.toMillis()} ms")

        return result
    }

    /**
     * Get travel details asynchronously.
     * @return [TravelDetails] containing the fetched data.
     */
    suspend fun getAsync(): TravelDetails {
        return measureExecutionTime {
            logger.info("Fetching travel details asynchronously from ${gatewayConfig.targetUrl}")
            getDetailsAsync()
        }
    }

    /**
     * Get travel details synchronously.
     * @return [TravelDetails] containing the fetched data.
     */
    suspend fun getSync(): TravelDetails {
        return measureExecutionTime {
            logger.info("Fetching travel details synchronously from ${gatewayConfig.targetUrl}")
            getDetailsSync()
        }
    }
}
