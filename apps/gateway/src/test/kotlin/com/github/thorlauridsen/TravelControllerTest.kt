package com.github.thorlauridsen

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.thorlauridsen.controller.TRAVEL_BASE_ENDPOINT
import com.github.thorlauridsen.controller.TravelController
import com.github.thorlauridsen.model.TravelDetails
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.okJson
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * Tests for [TravelController].
 *
 * This test uses WireMock to mock external HTTP services.
 * WireMock is started before all tests and stopped after all tests.
 * The test methods verify that the controller returns the expected travel details.
 * The test methods use [WebTestClient] to make requests to the controller endpoints.
 * [WebTestClient] has support for testing reactive web applications (Spring Boot Webflux).
 *
 * @param webTestClient [WebTestClient] bean.
 */
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class TravelControllerTest(
    @Autowired private val webTestClient: WebTestClient,
    @Autowired private val objectMapper: ObjectMapper,
) {

    private val wireMock: WireMockServer = WireMockServer(9561)

    @BeforeAll
    fun setupWireMock() {
        wireMock.start()
        setupWireMockStubs()
    }

    @AfterAll
    fun stopWireMock() {
        wireMock.stop()
    }

    fun setupWireMockStubs() {
        val hotels = objectMapper.writeValueAsString(TravelTestData.hotels)
        val flights = objectMapper.writeValueAsString(TravelTestData.flights)
        val rentalCars = objectMapper.writeValueAsString(TravelTestData.rentalCars)

        wireMock.stubFor(get("/hotels").willReturn(okJson(hotels)))
        wireMock.stubFor(get("/flights").willReturn(okJson(flights)))
        wireMock.stubFor(get("/rentalcars").willReturn(okJson(rentalCars)))
    }

    @Test
    fun `get travel details async - success`() {
        runTest {
            webTestClient.get()
                .uri("$TRAVEL_BASE_ENDPOINT/async")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(TravelDetails::class.java)
                .isEqualTo(TravelTestData.travelDetails)
        }
    }

    @Test
    fun `get travel details sync - success`() {
        runTest {
            webTestClient.get()
                .uri("$TRAVEL_BASE_ENDPOINT/sync")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(TravelDetails::class.java)
                .isEqualTo(TravelTestData.travelDetails)
        }
    }
}
