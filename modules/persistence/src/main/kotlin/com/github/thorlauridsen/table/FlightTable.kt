package com.github.thorlauridsen.table

import org.jetbrains.exposed.dao.id.UUIDTable

/**
 * Exposed UUID table for flights.
 * @property flightNumber Flight number.
 * @property airline Airline of the flight.
 * @property origin Origin of the flight.
 * @property destination Destination of the flight.
*/
object FlightTable : UUIDTable("flight") {
    val flightNumber = varchar("flight_number", 255)
    val airline = varchar("airline", 255)
    val origin = varchar("origin", 255)
    val destination = varchar("destination", 255)
}
