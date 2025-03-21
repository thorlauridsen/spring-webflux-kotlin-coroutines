package com.github.thorlauridsen

import com.github.thorlauridsen.model.Flight
import com.github.thorlauridsen.model.Hotel
import com.github.thorlauridsen.model.RentalCar
import com.github.thorlauridsen.dto.TravelDetailsDto

/**
 * Singleton object containing test data for [TravelDetailsDto].
 */
object TravelTestData {

    private val flights = listOf(
        Flight(flightNumber = "AB100", airline = "Airline A", origin = "Origin A", destination = "Destination A"),
        Flight(flightNumber = "AC101", airline = "Airline B", origin = "Origin B", destination = "Destination B"),
        Flight(flightNumber = "AD102", airline = "Airline C", origin = "Origin C", destination = "Destination C")
    )

    private val hotels = listOf(
        Hotel(name = "Hotel A", location = "Location A", rating = 4.5),
        Hotel(name = "Hotel B", location = "Location B", rating = 4.0),
        Hotel(name = "Hotel C", location = "Location C", rating = 3.8)
    )

    private val rentalCars = listOf(
        RentalCar(company = "Company A", carModel = "Car model A", location = "Location A"),
        RentalCar(company = "Company B", carModel = "Car model B", location = "Location B"),
        RentalCar(company = "Company C", carModel = "Car model C", location = "Location C")
    )

    val travelDetailsDto = TravelDetailsDto(
        flights = flights,
        hotels = hotels,
        rentalCars = rentalCars
    )
}
