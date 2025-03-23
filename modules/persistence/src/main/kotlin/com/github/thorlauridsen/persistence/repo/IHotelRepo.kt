package com.github.thorlauridsen.persistence.repo

import com.github.thorlauridsen.model.Hotel

/**
 * Flight repository interface.
 * This is an interface containing methods for interacting with the flight table.
 * A repository class will implement this interface to provide the actual implementation.
 * This interface makes it easier to swap out the implementation of the repository if needed.
 */
interface IHotelRepo {

    /**
     * Save a hotel to the database.
     * @param hotel [Hotel] to save.
     * @return [Hotel] retrieved from database.
     */
    fun save(hotel: Hotel): Hotel

    /**
     * Get all hotels from the database.
     * @return List of [Hotel]
     */
    fun findAll(): List<Hotel>
}
