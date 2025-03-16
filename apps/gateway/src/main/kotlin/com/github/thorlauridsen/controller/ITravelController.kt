package com.github.thorlauridsen.controller

import com.github.thorlauridsen.dto.TravelDetailsDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

const val TRAVEL_BASE_ENDPOINT = "/travel"

/**
 * Interface for [TravelController].
 * This interface defines the endpoints for the travel controller.
 * It also defines useful information for generating the Swagger documentation.
 */
@Tag(name = "Travel Controller", description = "API for retrieving travel information")
@RequestMapping(TRAVEL_BASE_ENDPOINT)
interface ITravelController {

    /**
     * Retrieve travel details asynchronously.
     * @return [TravelDetailsDto]
     */
    @GetMapping("/async")
    @Operation(
        summary = "Retrieve travel details",
        description = "Retrieve travel details"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved travel details"
    )
    suspend fun getAsync(): ResponseEntity<TravelDetailsDto>

    /**
     * Retrieve travel details synchronously.
     * @return [TravelDetailsDto]
     */
    @GetMapping("/sync")
    @Operation(
        summary = "Retrieve travel details",
        description = "Retrieve travel details"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved travel details"
    )
    suspend fun getSync(): ResponseEntity<TravelDetailsDto>
}
