plugins {
	kotlin("plugin.spring") version local.versions.kotlin
	alias(local.plugins.springboot)
	alias(local.plugins.spring.dependencies)
}

dependencies {
	// The provider subproject needs access to both the model and persistence subproject
	implementation(projects.model)
	implementation(projects.persistence)

	// Spring Boot dependencies
	implementation(local.springboot.starter)
	implementation(local.springboot.starter.webflux)

	// Kotlin Coroutines
	// Reactor is required for Spring Boot controller suspend endpoints
	implementation(local.kotlin.coroutines)
	implementation(local.kotlin.coroutines.reactor)

	// Springdoc for swagger docs supporting Spring WebFlux
	implementation(local.springdoc.openapi.starter.webflux)

	// FasterXML Jackson module for Kotlin support
	implementation(local.jackson.module.kotlin)

	// Liquibase for database migrations
	runtimeOnly(local.liquibase.core)

	// H2 in-memory database
	runtimeOnly(local.h2database)

	// PostgreSQL database driver
	runtimeOnly(local.postgres)

	// Test dependencies
	testImplementation(local.springboot.starter.test)
	testImplementation(local.kotlin.test.junit5)
	testImplementation(local.kotlin.coroutines.test)
	testRuntimeOnly(local.junit.platform.launcher)
}

tasks.test {
	useJUnitPlatform()
}
