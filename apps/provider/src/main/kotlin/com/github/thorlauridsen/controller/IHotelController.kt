package com.github.thorlauridsen.controller

import com.github.thorlauridsen.model.Hotel
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

const val HOTEL_BASE_ENDPOINT = "/hotels"

/**
 * Interface for [HotelController].
 * This interface defines the endpoints for the hotel controller.
 * It also defines useful information for generating the Swagger documentation.
 */
@Tag(name = "Hotel Controller", description = "API for managing hotels")
@RequestMapping(HOTEL_BASE_ENDPOINT)
interface IHotelController {

    /**
     * Retrieve all hotels
     * @return List of [Hotel]
     */
    @GetMapping
    @Operation(
        summary = "Retrieve all hotels",
        description = "Retrieve all hotels"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved hotels"
    )
    suspend fun getAll(): ResponseEntity<List<Hotel>>

    /**
     * Save a hotel.
     * @param hotel [Hotel] to save.
     * @return Saved [Hotel]
     */
    @PostMapping
    @Operation(
        summary = "Save a hotel",
        description = "Save a hotel"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Hotel successfully created"
    )
    suspend fun post(@Valid @RequestBody hotel: Hotel): ResponseEntity<Hotel>
}
