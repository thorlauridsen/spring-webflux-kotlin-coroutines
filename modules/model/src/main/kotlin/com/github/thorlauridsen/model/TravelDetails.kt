package com.github.thorlauridsen.model

/**
 * Model data class representing the details of travel options.
 * @param flights List of flights.
 * @param hotels List of hotels.
 * @param rentalCars List of rental cars.
 */
data class TravelDetails(
    val flights: List<Flight>,
    val hotels: List<Hotel>,
    val rentalCars: List<RentalCar>,
)
