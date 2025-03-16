package com.github.thorlauridsen.controller

import com.github.thorlauridsen.dto.TravelDetailsDto
import com.github.thorlauridsen.service.TravelService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

/**
 * REST controller for retrieving travel details.
 * @param travelService [TravelService] service layer.
 */
@Controller
class TravelController(private val travelService: TravelService) : ITravelController {

    /**
     * Retrieves travel details asynchronously.
     * @return [ResponseEntity] containing [TravelDetailsDto].
     */
    override suspend fun getAsync(): ResponseEntity<TravelDetailsDto> {
        return ResponseEntity.ok(travelService.getAsync())
    }

    /**
     * Retrieves travel details synchronously.
     * @return [ResponseEntity] containing [TravelDetailsDto].
     */
    override suspend fun getSync(): ResponseEntity<TravelDetailsDto> {
        return ResponseEntity.ok(travelService.getSync())
    }
}
