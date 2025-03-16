package com.github.thorlauridsen

import com.github.thorlauridsen.controller.HOTEL_BASE_ENDPOINT
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class HotelControllerTest(@Autowired private val client: WebTestClient) {

    @Test
    @Order(1)
    fun `get all hotels - success`() {
        client.get()
            .uri(HOTEL_BASE_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Hotel::class.java)
            .hasSize(3)
            .contains(
                Hotel(name = "Hotel A", location = "Location A", rating = 4.5),
                Hotel(name = "Hotel B", location = "Location B", rating = 4.0),
                Hotel(name = "Hotel C", location = "Location C", rating = 3.8)
            )
    }

    @Test
    @Order(2)
    fun `save hotel - success`() {
        val hotel = Hotel(name = "New Hotel", location = "New Location", rating = 5.0)

        client.post()
            .uri(HOTEL_BASE_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(hotel)
            .exchange()
            .expectStatus().isOk
            .expectBody(Hotel::class.java)
            .consumeWith { response ->
                val savedHotel = response.responseBody
                assertNotNull(savedHotel)
                assertEquals("New Hotel", savedHotel.name)
                assertEquals("New Location", savedHotel.location)
                assertEquals(5.0, savedHotel.rating)
            }
    }
}
