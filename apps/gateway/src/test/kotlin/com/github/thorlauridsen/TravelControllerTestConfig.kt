package com.github.thorlauridsen

import com.github.thorlauridsen.controller.TravelController
import com.github.thorlauridsen.service.TravelService
import org.mockito.Mockito
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration for testing [TravelController].
 * This configuration mocks [TravelService] and creates a [TravelController] bean.
 */
@Configuration
class TravelControllerTestConfig {

    @Bean
    fun travelService(): TravelService {
        return Mockito.mock(TravelService::class.java)
    }

    @Bean
    fun travelController(travelService: TravelService): TravelController {
        return TravelController(travelService)
    }
}
