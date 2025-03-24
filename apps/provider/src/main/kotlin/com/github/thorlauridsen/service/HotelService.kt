package com.github.thorlauridsen.service

import com.github.thorlauridsen.model.Hotel
import com.github.thorlauridsen.model.IHotelRepo
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * This service is responsible for:
 * - Saving hotels.
 * - Fetching hotels.
 *
 * @param hotelRepo [IHotelRepo] to interact with the database.
 */
@Service
class HotelService(private val hotelRepo: IHotelRepo) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Save a hotel.
     * @param hotel [Hotel] to save.
     * @return [Hotel] retrieved from database.
     */
    suspend fun save(hotel: Hotel): Hotel {
        logger.info("Saving hotel $hotel to database...")

        return hotelRepo.save(hotel)
    }

    /**
     * Get all hotels.
     * This function has an artificial delay of 2000ms to simulate a slow response.
     * @return [Hotel] or null if not found.
     */
    suspend fun findAll(): List<Hotel> {
        logger.info("Retrieving all hotels from database...")

        val rentalCars = hotelRepo.findAll()
        delay(2000)

        logger.info("Found ${rentalCars.size} hotels")
        return rentalCars
    }
}
