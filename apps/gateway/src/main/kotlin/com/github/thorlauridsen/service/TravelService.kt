package com.github.thorlauridsen.service

import com.github.thorlauridsen.Flight
import com.github.thorlauridsen.Hotel
import com.github.thorlauridsen.RentalCar
import com.github.thorlauridsen.dto.TravelDetailsDto
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
 */
@Service
class TravelService {

    private val targetUrl = "http://localhost:8081"
    private val logger = LoggerFactory.getLogger(TravelService::class.java)
    private val client = WebClient.create(targetUrl)

    /**
     * Get travel details asynchronously.
     * @return The travel details.
     */
    suspend fun getAsync(): TravelDetailsDto {
        logger.info("Fetching travel details asynchronously from $targetUrl")
        val start = OffsetDateTime.now()

        val travelDetails = coroutineScope {
            val hotelsDeferred = async { fetch<Hotel>("/hotels") }
            val flightsDeferred = async { fetch<Flight>("/flights") }
            val rentalCarsDeferred = async { fetch<RentalCar>("/rentalcars") }

            return@coroutineScope TravelDetailsDto(
                hotels = hotelsDeferred.await(),
                flights = flightsDeferred.await(),
                rentalCars = rentalCarsDeferred.await()
            )
        }
        val duration = Duration.between(start, OffsetDateTime.now())
        logger.info("Fetched travel details asynchronously in ${duration.toMillis()} ms")

        return travelDetails
    }

    /**
     * Get travel details synchronously.
     * @return The travel details.
     */
    suspend fun getSync(): TravelDetailsDto {
        logger.info("Fetching travel details synchronously from $targetUrl")
        val start = OffsetDateTime.now()

        val hotels = fetch<Hotel>("/hotels")
        val flights = fetch<Flight>("/flights")
        val rentalCars = fetch<RentalCar>("/rentalcars")

        val duration = Duration.between(start, OffsetDateTime.now())
        logger.info("Fetched travel details synchronously in ${duration.toMillis()} ms")

        return TravelDetailsDto(
            hotels = hotels,
            flights = flights,
            rentalCars = rentalCars
        )
    }

    /**
     * Fetch data from the target URL.
     * @param uri The URI to fetch data from.
     * @return A list of objects of type T.
     */
    private suspend inline fun <reified T> fetch(uri: String): List<T> =
        client.get()
            .uri(uri)
            .retrieve()
            .awaitBody()
}
