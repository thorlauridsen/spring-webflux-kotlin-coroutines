package com.github.thorlauridsen.model

/**
 * Model data class representing a rental car.
 * @param company Company that rents out the car.
 * @param carModel Model of the car.
 * @param location Location of the car.
 */
data class RentalCar(
    val company: String,
    val carModel: String,
    val location: String,
)
