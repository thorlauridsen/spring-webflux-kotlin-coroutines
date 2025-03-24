package com.github.thorlauridsen.controller

import com.github.thorlauridsen.model.TravelDetails
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

const val TRAVEL_BASE_ENDPOINT = "/travel"

/**
 * Travel controller interface.
 * This interface defines the endpoints for the travel controller.
 * It also defines the operations which will be used in the OpenAPI documentation.
 * The purpose with this interface is to separate the controller definition from the implementation.
 */
@Tag(name = "Travel Controller", description = "API for retrieving travel information")
@RequestMapping(TRAVEL_BASE_ENDPOINT)
interface ITravelController {

    /**
     * Retrieve travel details asynchronously.
     * @return [ResponseEntity] with [TravelDetails].
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
    suspend fun getAsync(): ResponseEntity<TravelDetails>

    /**
     * Retrieve travel details synchronously.
     * @return [ResponseEntity] with [TravelDetails].
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
    suspend fun getSync(): ResponseEntity<TravelDetails>
}
