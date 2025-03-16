plugins {
	kotlin("plugin.spring") version local.versions.kotlin
	alias(local.plugins.springboot)
	alias(local.plugins.spring.dependencies)
}

dependencies {
	// The gateway subproject needs access to just the model subproject
	implementation(projects.model)

	// Spring Boot dependencies
	implementation(local.springboot.starter)
	implementation(local.springboot.starter.webflux)

	// Kotlin Coroutines
	// Reactor is required for Spring Boot controller suspend endpoints
	implementation(local.kotlin.coroutines)
	implementation(local.kotlin.coroutines.reactor)
	testImplementation(local.kotlin.coroutines.test)

	// SpringDoc for swagger docs supporting Spring Webflux
	implementation(local.springdoc.openapi.starter.webflux)

	// FasterXML Jackson module for Kotlin support
	implementation(local.jackson.module.kotlin)

	// Test dependencies
	testImplementation(local.springboot.starter.test)
	testImplementation(local.kotlin.test.junit5)
	testRuntimeOnly(local.junit.platform.launcher)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
