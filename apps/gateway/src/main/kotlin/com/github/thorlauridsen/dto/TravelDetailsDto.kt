package com.github.thorlauridsen.dto

import com.github.thorlauridsen.model.Flight
import com.github.thorlauridsen.model.Hotel
import com.github.thorlauridsen.model.RentalCar

/**
 * Data transfer object representing the details of travel options
 * @param flights List of flights.
 * @param hotels List of hotels.
 * @param rentalCars List of rental cars.
 */
data class TravelDetailsDto(
    val flights: List<Flight>,
    val hotels: List<Hotel>,
    val rentalCars: List<RentalCar>,
)
