package com.github.thorlauridsen.controller

import com.github.thorlauridsen.model.Flight
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

const val FLIGHT_BASE_ENDPOINT = "/flights"

/**
 * Flight controller interface.
 * This interface defines the endpoints for the flight controller.
 * It also defines the operations which will be used in the OpenAPI documentation.
 * The purpose with this interface is to separate the controller definition from the implementation.
 */
@Tag(name = "Flight Controller", description = "API for managing flights")
@RequestMapping(FLIGHT_BASE_ENDPOINT)
interface IFlightController {

    /**
     * Retrieve all flights.
     * @return List of [Flight].
     */
    @GetMapping
    @Operation(
        summary = "Retrieve all flights",
        description = "Retrieve all flights"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved flights"
    )
    suspend fun getAll(): ResponseEntity<List<Flight>>

    /**
     * Save a flight.
     * @param flight [Flight] to save.
     * @return Saved [Flight].
     */
    @PostMapping
    @Operation(
        summary = "Save a flight",
        description = "Save a flight"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Flight successfully created"
    )
    suspend fun post(@Valid @RequestBody flight: Flight): ResponseEntity<Flight>
}
