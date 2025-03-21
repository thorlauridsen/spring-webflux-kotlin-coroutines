package com.github.thorlauridsen.persistence.table

import org.jetbrains.exposed.dao.id.UUIDTable

/**
 * Exposed UUID table for hotels.
 * @param name Name of the hotel.
 * @param location Location of the hotel.
 * @param rating Rating of the hotel.
*/
object HotelTable : UUIDTable("hotel") {
    val name = varchar("hotel_name", 255)
    val location = varchar("location", 255)
    val rating = double("rating")
}
