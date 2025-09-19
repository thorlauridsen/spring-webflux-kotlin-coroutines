package com.github.thorlauridsen.controller

import com.github.thorlauridsen.model.RentalCar
import com.github.thorlauridsen.service.RentalCarService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * This REST controller consists of endpoints for:
 * - Retrieving all rental cars.
 * - Saving rental cars.
 *
 * This class implements the [IRentalCarController] interface and
 * overrides the methods defined in the interface with implementations.
 *
 * @param rentalCarService [RentalCarService] service layer.
 */
@RestController
class RentalCarController(private val rentalCarService: RentalCarService) : IRentalCarController {

    /**
     * Retrieve all rental cars.
     * @return [ResponseEntity] with a list of [RentalCar].
     */
    override suspend fun getAll(): ResponseEntity<List<RentalCar>> {
        val list = rentalCarService.findAll()
        return ResponseEntity.ok(list)
    }

    /**
     * Save a rental car.
     * @param rentalCar [RentalCar] to save.
     * @return [ResponseEntity] with the saved [RentalCar].
     */
    override suspend fun post(rentalCar: RentalCar): ResponseEntity<RentalCar> {
        val savedRentalCar = rentalCarService.save(rentalCar)
        return ResponseEntity.ok(savedRentalCar)
    }
}
