package com.github.thorlauridsen

/**
 * Model data class representing a flight.
 * @param flightNumber Flight number.
 * @param airline Airline of the flight.
 * @param origin Origin of the flight.
 * @param destination Destination of the flight.
 */
data class Flight(
    val flightNumber: String,
    val airline: String,
    val origin: String,
    val destination: String,
)
