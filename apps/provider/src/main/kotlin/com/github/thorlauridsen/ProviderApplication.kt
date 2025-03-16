package com.github.thorlauridsen

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Main entry point for the provider application.
 */
@SpringBootApplication
class ProviderApplication

fun main(args: Array<String>) {
	runApplication<ProviderApplication>(*args)
}
