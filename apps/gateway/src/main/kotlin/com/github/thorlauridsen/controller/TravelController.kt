package com.github.thorlauridsen.controller

import com.github.thorlauridsen.model.TravelDetails
import com.github.thorlauridsen.service.TravelService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * This REST controller consists of endpoints for:
 * - Fetching travel details asynchronously.
 * - Fetching travel details synchronously.
 *
 * This class implements the [ITravelController] interface and
 * overrides the methods defined in the interface with implementations.
 *
 * @param travelService [TravelService] service layer.
 */
@RestController
class TravelController(private val travelService: TravelService) : ITravelController {

    /**
     * Retrieves travel details asynchronously.
     * @return [ResponseEntity] with [TravelDetails].
     */
    override suspend fun getAsync(): ResponseEntity<TravelDetails> {
        return ResponseEntity.ok(travelService.getAsync())
    }

    /**
     * Retrieves travel details synchronously.
     * @return [ResponseEntity] with [TravelDetails].
     */
    override suspend fun getSync(): ResponseEntity<TravelDetails> {
        return ResponseEntity.ok(travelService.getSync())
    }
}
