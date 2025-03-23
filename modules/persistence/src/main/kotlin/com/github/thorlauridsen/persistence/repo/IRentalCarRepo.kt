package com.github.thorlauridsen.persistence.repo

import com.github.thorlauridsen.model.RentalCar

/**
 * Flight repository interface.
 * This is an interface containing methods for interacting with the flight table.
 * A repository class will implement this interface to provide the actual implementation.
 * This interface makes it easier to swap out the implementation of the repository if needed.
 */
interface IRentalCarRepo {

    /**
     * Save a rental car to the database.
     * @param rentalCar [RentalCar] to save.
     * @return [RentalCar] retrieved from database.
     */
    fun save(rentalCar: RentalCar): RentalCar

    /**
     * Get all rental cars from the database.
     * @return List of [RentalCar]
     */
    fun findAll(): List<RentalCar>
}
