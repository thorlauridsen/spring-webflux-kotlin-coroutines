package com.github.thorlauridsen.persistence.table

import org.jetbrains.exposed.dao.id.UUIDTable

/**
 * Exposed UUID table for rental cars.
 * @param company Company that rents out the car.
 * @param carModel Model of the car.
 * @param location Location of the car.
*/
object RentalCarTable : UUIDTable("rental_car") {
    val company = varchar("company", 255)
    val carModel = varchar("car_model", 255)
    val location = varchar("location", 255)
}
