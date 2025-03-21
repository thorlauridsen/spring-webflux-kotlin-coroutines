package com.github.thorlauridsen.controller

import com.github.thorlauridsen.model.Flight
import com.github.thorlauridsen.service.FlightService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

/**
 * REST controller for flights.
 * This controller consists of endpoints for:
 * - Saving flights.
 * - Fetching flights.
 *
 * @param flightService [FlightService] service layer.
 */
@Controller
class FlightController(private val flightService: FlightService) : IFlightController {

    /**
     * Retrieve all flights
     * @return List of [Flight]
     */
    override suspend fun getAll(): ResponseEntity<List<Flight>> {
        val list = flightService.findAll()
        return ResponseEntity.ok(list)
    }

    /**
     * Save a flight.
     * @param flight [Flight] to save.
     * @return Saved [Flight]
     */
    override suspend fun post(flight: Flight): ResponseEntity<Flight> {
        val savedFlight = flightService.save(flight)
        return ResponseEntity.ok(savedFlight)
    }
}
