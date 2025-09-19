package com.github.thorlauridsen.controller

import com.github.thorlauridsen.model.Flight
import com.github.thorlauridsen.service.FlightService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * This REST controller consists of endpoints for:
 * - Retrieving all flights.
 * - Saving flights.
 *
 * This class implements the [IFlightController] interface and
 * overrides the methods defined in the interface with implementations.
 *
 * @param flightService [FlightService] service layer.
 */
@RestController
class FlightController(private val flightService: FlightService) : IFlightController {

    /**
     * Retrieve all flights.
     * @return [ResponseEntity] with a list of [Flight].
     */
    override suspend fun getAll(): ResponseEntity<List<Flight>> {
        val list = flightService.findAll()
        return ResponseEntity.ok(list)
    }

    /**
     * Save a flight.
     * @param flight [Flight] to save.
     * @return [ResponseEntity] with the saved [Flight].
     */
    override suspend fun post(flight: Flight): ResponseEntity<Flight> {
        val savedFlight = flightService.save(flight)
        return ResponseEntity.ok(savedFlight)
    }
}
