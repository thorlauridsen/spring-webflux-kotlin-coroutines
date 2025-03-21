package com.github.thorlauridsen.service

import com.github.thorlauridsen.RentalCar
import com.github.thorlauridsen.repo.RentalCarRepo
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * This service is responsible for:
 * - Saving rental cars.
 * - Fetching rental cars.
 *
 * @param rentalCarRepo Exposed [RentalCarRepo] to interact with the database.
 */
@Service
class RentalCarService(private val rentalCarRepo: RentalCarRepo) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Save a rental car.
     * @param rentalCar [RentalCar] to save.
     * @return [RentalCar] retrieved from database.
     */
    suspend fun save(rentalCar: RentalCar): RentalCar {
        logger.info("Saving rental car $rentalCar to database...")

        return rentalCarRepo.save(rentalCar)
    }

    /**
     * Get all rental cars.
     * This function has an artificial delay of 2000ms to simulate a slow response.
     * @return [RentalCar] or null if not found.
     */
    suspend fun findAll(): List<RentalCar> {
        logger.info("Retrieving all rental cars from database...")

        val rentalCars = rentalCarRepo.findAll()
        delay(2000)

        logger.info("Found ${rentalCars.size} rental cars")
        return rentalCars
    }
}
