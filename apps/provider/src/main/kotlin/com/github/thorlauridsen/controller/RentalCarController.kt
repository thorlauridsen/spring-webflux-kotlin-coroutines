package com.github.thorlauridsen.controller

import com.github.thorlauridsen.model.RentalCar
import com.github.thorlauridsen.service.RentalCarService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

/**
 * REST controller for rental cars.
 * This controller consists of endpoints for:
 * - Saving rental cars.
 * - Fetching rental cars.
 *
 * @param rentalCarService [RentalCarService] service layer.
 */
@Controller
class RentalCarController(private val rentalCarService: RentalCarService) : IRentalCarController {

    /**
     * Retrieve all rental cars
     * @return List of [RentalCar]
     */
    override suspend fun getAll(): ResponseEntity<List<RentalCar>> {
        val list = rentalCarService.findAll()
        return ResponseEntity.ok(list)
    }

    /**
     * Save a rental car.
     * @param rentalCar [RentalCar] to save.
     * @return Saved [RentalCar]
     */
    override suspend fun post(rentalCar: RentalCar): ResponseEntity<RentalCar> {
        val savedRentalCar = rentalCarService.save(rentalCar)
        return ResponseEntity.ok(savedRentalCar)
    }
}
