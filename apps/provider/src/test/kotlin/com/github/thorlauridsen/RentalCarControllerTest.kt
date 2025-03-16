package com.github.thorlauridsen

import com.github.thorlauridsen.controller.RENTAL_CAR_BASE_ENDPOINT
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Tests for RentalCarController.
 *
 * The test methods verify that the controller returns the expected rental cars.
 * The test methods use [WebTestClient] to make requests to the controller endpoints.
 * [WebTestClient] has support for testing reactive web applications (Spring Boot Webflux).
 * The tests are annotated with [Order] to specify the order in which they should run.
 * Otherwise, the order of test execution is not guaranteed and the database state may change between tests.
 *
 * @param client [WebTestClient] bean.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RentalCarControllerTest(@Autowired private val client: WebTestClient) {

    @Test
    @Order(1)
    fun `get all rental cars - success`() {
        client.get()
            .uri(RENTAL_CAR_BASE_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(RentalCar::class.java)
            .hasSize(3)
            .contains(
                RentalCar(company = "Company A", carModel = "Car model A", location = "Location A"),
                RentalCar(company = "Company B", carModel = "Car model B", location = "Location B"),
                RentalCar(company = "Company C", carModel = "Car model C", location = "Location C")
            )
    }

    @Test
    @Order(2)
    fun `save rental car - success`() {
        val rentalCar = RentalCar(
            company = "New Company",
            carModel = "New Model",
            location = "New Location"
        )
        client.post()
            .uri(RENTAL_CAR_BASE_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(rentalCar)
            .exchange()
            .expectStatus().isOk
            .expectBody(RentalCar::class.java)
            .consumeWith { response ->
                val savedRentalCar = response.responseBody
                assertNotNull(savedRentalCar)
                assertEquals("New Company", savedRentalCar.company)
                assertEquals("New Model", savedRentalCar.carModel)
                assertEquals("New Location", savedRentalCar.location)
            }
    }
}
