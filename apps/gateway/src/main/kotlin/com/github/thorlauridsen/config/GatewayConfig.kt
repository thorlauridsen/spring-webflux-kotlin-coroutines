package com.github.thorlauridsen.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Configuration properties for the gateway subproject.
 * @param targetUrl URL of the target service.
 */
@ConfigurationProperties(prefix = "gateway.settings")
class GatewayConfig(
    /**
     * URL of the target service.
     */
    val targetUrl: String,
)
