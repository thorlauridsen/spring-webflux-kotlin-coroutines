package com.github.thorlauridsen

import com.github.thorlauridsen.config.GatewayConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

/**
 * Main entry point for the gateway application.
 * We need to enable configuration properties for [GatewayConfig].
 * This ensures that Spring Boot will bind the configuration
 * properties from application.yml to the [GatewayConfig] class.
 */
@SpringBootApplication
@EnableConfigurationProperties(
	GatewayConfig::class,
)
class GatewayApplication

fun main(args: Array<String>) {
	runApplication<GatewayApplication>(*args)
}
