package com.github.thorlauridsen.controller

import com.github.thorlauridsen.model.Hotel
import com.github.thorlauridsen.service.HotelService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

/**
 * This REST controller consists of endpoints for:
 * - Retrieving all hotels.
 * - Saving hotels.
 *
 * This class implements the [IHotelController] interface and
 * overrides the methods defined in the interface with implementations.
 *
 * @param hotelService [HotelService] service layer.
 */
@Controller
class HotelController(private val hotelService: HotelService) : IHotelController {

    /**
     * Retrieve all hotels.
     * @return List of [Hotel].
     */
    override suspend fun getAll(): ResponseEntity<List<Hotel>> {
        val list = hotelService.findAll()
        return ResponseEntity.ok(list)
    }

    /**
     * Save a hotel.
     * @param hotel [Hotel] to save.
     * @return Saved [Hotel].
     */
    override suspend fun post(hotel: Hotel): ResponseEntity<Hotel> {
        val savedHotel = hotelService.save(hotel)
        return ResponseEntity.ok(savedHotel)
    }
}
