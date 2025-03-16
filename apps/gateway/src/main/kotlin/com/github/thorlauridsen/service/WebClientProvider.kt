package com.github.thorlauridsen.service

import com.github.thorlauridsen.config.GatewayConfig
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

/**
 * Provides a [WebClient] bean.
 * @param gatewayConfig [GatewayConfig] configuration.
 */
@Component
class WebClientProvider(private val gatewayConfig: GatewayConfig) {

    /**
     * Creates a [WebClient] bean with the target URL from [GatewayConfig].
     * @return [WebClient] bean.
     */
    @Bean
    fun webClient(): WebClient {
        return WebClient.create(gatewayConfig.targetUrl)
    }
}
