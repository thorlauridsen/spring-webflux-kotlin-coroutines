package com.github.thorlauridsen

import com.github.thorlauridsen.model.Flight
import com.github.thorlauridsen.controller.FLIGHT_BASE_ENDPOINT
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
 * Tests for FlightController.
 *
 * The test methods verify that the controller returns the expected flights.
 * The test methods use [WebTestClient] to make requests to the controller endpoints.
 * [WebTestClient] has support for testing reactive web applications (Spring Boot Webflux).
 * The tests are annotated with [Order] to specify the order in which they should run.
 * Otherwise, the order of test execution is not guaranteed and the database state may change between tests.
 *
 * @param client [WebTestClient] bean.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class FlightControllerTest(@Autowired private val client: WebTestClient) {

    @Test
    @Order(1)
    fun `get all flights - success`() {
        client.get()
            .uri(FLIGHT_BASE_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Flight::class.java)
            .hasSize(4)
            .contains(
                Flight(
                    flightNumber = "AB100",
                    airline = "Airline A",
                    origin = "Origin A",
                    destination = "Destination A"
                ),
                Flight(
                    flightNumber = "AC101",
                    airline = "Airline B",
                    origin = "Origin B",
                    destination = "Destination B"
                ),
                Flight(
                    flightNumber = "AD102",
                    airline = "Airline C",
                    origin = "Origin C",
                    destination = "Destination C"
                )
            )
    }

    @Test
    @Order(2)
    fun `save flight - success`() {
        val flight = Flight(
            flightNumber = "XY999",
            airline = "New Airline",
            origin = "New Origin",
            destination = "New Destination"
        )
        client.post()
            .uri(FLIGHT_BASE_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(flight)
            .exchange()
            .expectStatus().isOk
            .expectBody(Flight::class.java)
            .consumeWith { response ->
                val savedFlight = response.responseBody
                assertNotNull(savedFlight)
                assertEquals("XY999", savedFlight.flightNumber)
                assertEquals("New Airline", savedFlight.airline)
                assertEquals("New Origin", savedFlight.origin)
                assertEquals("New Destination", savedFlight.destination)
            }
    }
}
