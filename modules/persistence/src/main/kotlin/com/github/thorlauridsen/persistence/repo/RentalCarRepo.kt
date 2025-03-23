package com.github.thorlauridsen.persistence.repo

import com.github.thorlauridsen.model.IRentalCarRepo
import com.github.thorlauridsen.model.RentalCar
import com.github.thorlauridsen.persistence.table.RentalCarTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.UUID

/**
 * Exposed rental car repository.
 * This repository is responsible for:
 * - Saving rental cars to the database.
 * - Fetching rental cars from the database.
 */
@Repository
class RentalCarRepo : IRentalCarRepo {

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Save a rental car to the database.
     * @param rentalCar [RentalCar] to save.
     * @throws IllegalStateException if rental car not found in database after saving.
     * @return [RentalCar] retrieved from database.
     */
    override fun save(rentalCar: RentalCar): RentalCar {
        return transaction {
            logger.info("Saving rental car $rentalCar to database...")
            val id = RentalCarTable.insertAndGetId {
                it[company] = rentalCar.company
                it[carModel] = rentalCar.carModel
                it[location] = rentalCar.location
            }
            logger.info("Saved rental car with id $id in database")

            find(id.value) ?: error("Could not save rental car with id: $rentalCar")
        }
    }

    /**
     * Get all rental cars from the database.
     * @return List of [RentalCar]
     */
    override fun findAll(): List<RentalCar> {
        logger.info("Retrieving all rental cars from database...")

        return transaction {
            val rentalCars = RentalCarTable
                .selectAll()
                .map { mapToModel(it) }

            logger.info("Found ${rentalCars.size} rental cars in database")
            rentalCars
        }
    }

    /**
     * Get a rental car from the database given an id.
     * @param id [UUID] to fetch rental car.
     * @return [RentalCar] or null if not found.
     */
    private fun find(id: UUID): RentalCar? {
        logger.info("Retrieving rental car with id: $id from database...")

        return transaction {
            val rentalCar = RentalCarTable
                .selectAll()
                .where { RentalCarTable.id eq id }
                .map { mapToModel(it) }
                .firstOrNull()

            logger.info("Found rental car $rentalCar in database")
            rentalCar
        }
    }

    /**
     * Map ResultRow to a RentalCar.
     * @param row [ResultRow]
     * @return [RentalCar]
     */
    private fun mapToModel(row: ResultRow): RentalCar {
        return RentalCar(
            company = row[RentalCarTable.company],
            carModel = row[RentalCarTable.carModel],
            location = row[RentalCarTable.location],
        )
    }
}
