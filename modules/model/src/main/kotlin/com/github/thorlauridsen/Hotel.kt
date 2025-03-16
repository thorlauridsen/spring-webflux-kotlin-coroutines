package com.github.thorlauridsen

/**
 * Model data class representing a hotel.
 * @param name Name of the hotel.
 * @param location Location of the hotel.
 * @param rating Rating of the hotel.
 */
data class Hotel(
    val name: String,
    val location: String,
    val rating: Double,
)
