package com.github.thorlauridsen.repo

import com.github.thorlauridsen.Hotel
import com.github.thorlauridsen.table.HotelTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.UUID

/**
 * Exposed hotel repository.
 * This repository is responsible for:
 * - Saving hotels to the database.
 * - Fetching hotels from the database.
 */
@Repository
class HotelRepo {

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Save a hotel to the database.
     * @param hotel [Hotel] to save.
     * @throws IllegalStateException if hotel not found in database after saving.
     * @return [Hotel] retrieved from database.
     */
    fun save(hotel: Hotel): Hotel {
        return transaction {
            logger.info("Saving hotel $hotel to database...")
            val id = HotelTable.insertAndGetId {
                it[name] = hotel.name
                it[location] = hotel.location
                it[rating] = hotel.rating
            }
            logger.info("Saved hotel with id $id in database")

            find(id.value) ?: error("Could not save hotel with id: $hotel")
        }
    }

    /**
     * Get all hotels from the database.
     * @return List of [Hotel]
     */
    fun findAll(): List<Hotel> {
        logger.info("Retrieving all hotels from database...")

        return transaction {
            val hotels = HotelTable
                .selectAll()
                .map { mapToModel(it) }

            logger.info("Found ${hotels.size} hotels in database")
            hotels
        }
    }

    /**
     * Get a hotel from the database given an id.
     * @param id [UUID] to fetch hotel.
     * @return [Hotel] or null if not found.
     */
    private fun find(id: UUID): Hotel? {
        logger.info("Retrieving hotel with id: $id from database...")

        return transaction {
            val hotel = HotelTable
                .selectAll()
                .where { HotelTable.id eq id }
                .map { mapToModel(it) }
                .firstOrNull()

            logger.info("Found hotel $hotel in database")
            hotel
        }
    }

    /**
     * Map ResultRow to a Hotel.
     * @param row [ResultRow]
     * @return [Hotel]
     */
    private fun mapToModel(row: ResultRow): Hotel {
        return Hotel(
            name = row[HotelTable.name],
            location = row[HotelTable.location],
            rating = row[HotelTable.rating]
        )
    }
}
