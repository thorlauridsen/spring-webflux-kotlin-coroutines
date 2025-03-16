package com.github.thorlauridsen.repo

import com.github.thorlauridsen.Flight
import com.github.thorlauridsen.table.FlightTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.UUID

/**
 * Exposed flight repository.
 * This repository is responsible for:
 * - Saving flights to the database.
 * - Fetching flights from the database.
 */
@Repository
class FlightRepo {

    private val logger = LoggerFactory.getLogger(FlightRepo::class.java)

    /**
     * Save a flight to the database.
     * @param flight [Flight] to save.
     * @throws IllegalStateException if flight not found in database after saving.
     * @return [Flight] retrieved from database.
     */
    fun save(flight: Flight): Flight {
        return transaction {
            logger.info("Saving flight $flight to database...")
            val id = FlightTable.insertAndGetId {
                it[flightNumber] = flight.flightNumber
                it[airline] = flight.airline
                it[origin] = flight.origin
                it[destination] = flight.destination
            }
            logger.info("Saved flight with id $id in database")

            find(id.value) ?: error("Could not save flight with id: $flight")
        }
    }

    /**
     * Get all flights from the database.
     * @return List of [Flight]
     */
    fun findAll(): List<Flight> {
        logger.info("Retrieving all flights from database...")

        return transaction {
            val flights = FlightTable
                .selectAll()
                .map { mapToModel(it) }

            logger.info("Found ${flights.size} flights in database")
            flights
        }
    }

    /**
     * Get a flight from the database given an id.
     * @param id [UUID] to fetch flight.
     * @return [Flight] or null if not found.
     */
    private fun find(id: UUID): Flight? {
        logger.info("Retrieving flight with id: $id from database...")

        return transaction {
            val flight = FlightTable
                .selectAll()
                .where { FlightTable.id eq id }
                .map { mapToModel(it) }
                .firstOrNull()

            logger.info("Found flight $flight in database")
            flight
        }
    }

    /**
     * Map ResultRow to a Flight.
     * @param row [ResultRow]
     * @return [Flight]
     */
    private fun mapToModel(row: ResultRow): Flight {
        return Flight(
            flightNumber = row[FlightTable.flightNumber],
            airline = row[FlightTable.airline],
            origin = row[FlightTable.origin],
            destination = row[FlightTable.destination],
        )
    }
}
