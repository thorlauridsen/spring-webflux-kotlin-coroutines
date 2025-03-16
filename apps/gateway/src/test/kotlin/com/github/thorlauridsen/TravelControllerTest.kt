package com.github.thorlauridsen

import com.github.thorlauridsen.controller.TRAVEL_BASE_ENDPOINT
import com.github.thorlauridsen.controller.TravelController
import com.github.thorlauridsen.dto.TravelDetailsDto
import com.github.thorlauridsen.service.TravelService
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * Tests for [TravelController].
 *
 * This test uses [TravelControllerTestConfig] to mock [TravelService].
 * The test methods verify that the controller returns the expected travel details.
 * The test methods use [WebTestClient] to make requests to the controller endpoints.
 * [WebTestClient] has support for testing reactive web applications (Spring Boot Webflux).
 *
 * @param travelService Mocked [TravelService] bean.
 * @param webTestClient [WebTestClient] bean.
 */
@WebFluxTest(TravelController::class)
@ContextConfiguration(classes = [TravelControllerTestConfig::class])
@AutoConfigureWebTestClient
class TravelControllerTest(
    @Autowired private val travelService: TravelService,
    @Autowired private val webTestClient: WebTestClient
) {

    @Test
    fun `get travel details async - success`() {
        runTest {
            Mockito.`when`(travelService.getAsync()).thenReturn(TravelTestData.travelDetailsDto)

            webTestClient.get()
                .uri("$TRAVEL_BASE_ENDPOINT/async")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(TravelDetailsDto::class.java)
                .isEqualTo(TravelTestData.travelDetailsDto)
        }
    }

    @Test
    fun `get travel details sync - success`() {
        runTest {
            Mockito.`when`(travelService.getSync()).thenReturn(TravelTestData.travelDetailsDto)

            webTestClient.get()
                .uri("$TRAVEL_BASE_ENDPOINT/sync")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(TravelDetailsDto::class.java)
                .isEqualTo(TravelTestData.travelDetailsDto)
        }
    }
}
