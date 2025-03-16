package com.github.thorlauridsen.service

import com.github.thorlauridsen.Flight
import com.github.thorlauridsen.repo.FlightRepo
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * This service is responsible for:
 * - Saving flights.
 * - Fetching flights.
 *
 * @param flightRepo Exposed [FlightRepo] to interact with the database.
 */
@Service
class FlightService(private val flightRepo: FlightRepo) {

    private val logger = LoggerFactory.getLogger(FlightService::class.java)

    /**
     * Save a flight.
     * @param flight [Flight] to save.
     * @return [Flight] retrieved from database.
     */
    suspend fun save(flight: Flight): Flight {
        logger.info("Saving flight $flight to database...")

        return flightRepo.save(flight)
    }

    /**
     * Get all flights.
     * This function has an artificial delay of 2000ms to simulate a slow response.
     * @return [Flight] or null if not found.
     */
    suspend fun findAll(): List<Flight> {
        logger.info("Retrieving all flights from database...")

        val rentalCars = flightRepo.findAll()
        delay(2000)

        logger.info("Found ${rentalCars.size} flights")
        return rentalCars
    }
}
