# Spring Boot Kotlin async sample

This is a sample project using 
[Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and
[Spring Webflux](https://docs.spring.io/spring-framework/reference/web/webflux.html) 
while utilizing async/await to optimize performance when executing multiple requests
to one or more remote services. This is useful in the case where the
data from one request is not required to continue the next request.

## Scenario

Imagine you are building a service which needs to present traveling/vacation offers to a client.
This could for example include offers such as:
- Flights
- Hotels
- Rental cars

You find a third-party service provider for each type of offer but you are forced
to send 3 requests to fetch all the relevant offers before returning it to the client.
The issue here is that with synchronous code, you would have to execute one
request at a time which could result in a slow response time.

## Services

### Provider REST API
The **provider** subproject is independently runnable and will spin up a Spring Boot REST API.
This service includes the following endpoints:
- `GET /flights`
- `GET /hotels`
- `GET /rentalcars`

Each endpoint, will return the full list of available entities from the database.
An artificial delay of 2000 milliseconds has been implemented for each endpoint. 
The purpose of this is to showcase the performance benefits when 
correctly using Kotlin Coroutines and async/await.

The **provider** subproject implements both the **model** and **persistence** subprojects. 
It can interact with an in-memory [H2database](https://github.com/h2database/h2database) 
using [Exposed](https://github.com/JetBrains/Exposed). 
Additionally, it uses [Liquibase](https://github.com/liquibase/liquibase) 
for database changelogs, where dummy data has been added to the database.

### Gateway REST API
The **gateway** subproject is independently runnable and will spin up a Spring Boot REST API.
This service includes the following endpoints:
- `GET /travel/async`
- `GET /travel/sync`

For this project, we use Spring Webflux and Kotlin Coroutines so we can achieve optimized performance.

This means we need the following Gradle dependencies in our local version catalog
[local.versions.toml](gradle/local.versions.toml)
```toml
[versions]
coroutines = "1.10.1"
springboot = "3.4.3"
springdoc = "2.8.5"

[libraries]
springboot-starter = { module = "org.springframework.boot:spring-boot-starter", version.ref = "springboot" }
springboot-starter-webflux = { module = "org.springframework.boot:spring-boot-starter-webflux", version.ref = "springboot" }

kotlin-coroutines = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "coroutines" }
kotlin-coroutines-reactor = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor", version.ref = "coroutines" }

springdoc-openapi-starter-webflux = { module = "org.springdoc:springdoc-openapi-starter-webflux-ui", version.ref = "springdoc" }
```

Spring Webflux and Kotlin Coroutines Reactor allows us to set controller functions as 
suspending functions. This means that every time a request is sent to an endpoint,
a coroutine will be launched for the specific request. This allows us to be
within a coroutine scope when executing requests. Additionally, Spring Webflux
provides [WebClient](https://docs.spring.io/spring-framework/reference/web/webflux-webclient.html)
which is a HTTP client based on Reactor to support asynchronous code.

The code below from [TravelService.kt](/apps\gateway/src/main/kotlin/com/github/thorlauridsen/service/TravelService.kt)
showcases how to use `async {}` and `.await()` to execute 3 requests simultaneously.

```kotlin
private val client = WebClient.create("http://localhost:8081")

private suspend inline fun <reified T> fetch(uri: String): List<T> {
    return client.get()
        .uri(uri)
        .retrieve()
        .awaitBody()
}

private suspend fun getDetailsAsync(): TravelDetailsDto {
    return coroutineScope {
        val hotelsDeferred = async { fetch<Hotel>("/hotels") }
        val flightsDeferred = async { fetch<Flight>("/flights") }
        val rentalCarsDeferred = async { fetch<RentalCar>("/rentalcars") }

        return@coroutineScope TravelDetailsDto(
            hotels = hotelsDeferred.await(),
            flights = flightsDeferred.await(),
            rentalCars = rentalCarsDeferred.await()
        )
    }
}
```

The benefit is that we do not have to wait for one request to finish
before starting the next request. The combined response time will be
approximately the same duration as the slowest of the three requests.

You can see an example of how the data is 
fetched synchronously in the code below:
```kotlin
private suspend fun getDetailsSync(): TravelDetailsDto {
    val hotels = fetch<Hotel>("/hotels")
    val flights = fetch<Flight>("/flights")
    val rentalCars = fetch<RentalCar>("/rentalcars")

    return TravelDetailsDto(
        hotels = hotels,
        flights = flights,
        rentalCars = rentalCars
    )
}
```
The project has Gradle depedencies for Kotlin Coroutines and
Spring Webflux which allows us to set the controller functions
to suspending functions. However, this is not enough in itself
to achieve optimized performance. If we do not use async/await
it will execute one request at a time.

This separate function has been added to showcase the differences 
in performance when running synchronous and asynchronous code.
Example logs can be seen below:

```yaml
14:16:45.736 : Fetching travel details synchronously from http://localhost:8081
14:16:45.736 : Executing request HTTP GET /hotels
14:16:48.413 : Executing request HTTP GET /flights
14:16:50.468 : Executing request HTTP GET /rentalcars
14:16:52.513 : Fetched travel details in 6776 ms
14:16:56.217 : Fetching travel details asynchronously from http://localhost:8081
14:16:56.225 : Executing request HTTP GET /hotels
14:16:56.227 : Executing request HTTP GET /flights
14:16:56.229 : Executing request HTTP GET /rentalcars
14:16:58.257 : Fetched travel details in 2041 ms
```

When fetching data from **n** independent external services:

#### Total execution time
- **Synchronous code**: Sum of individual request times `T_sync = t₁ + t₂ + ... + tₙ`
- **Asynchronous code**: Duration of the slowest request `T_async = max(t₁, t₂, ..., tₙ)`

## Usage
Clone the project to your local machine, go to the root directory and use
these two commands in separate terminals.
```
./gradlew gateway:bootRun
```
```
./gradlew provider:bootRun
```
You can also use IntelliJ IDEA to easily run the two services at once.

### Swagger Documentation
Once both services is running, you can navigate to http://localhost:8080/ 
and http://localhost:8081/ to view the Swagger documentation for each service.

## Technology
- [JDK21](https://openjdk.org/projects/jdk/21/) - Latest JDK with long-term support 
- [Kotlin](https://github.com/JetBrains/kotlin) - Programming language with Java interoperability
- [Gradle](https://github.com/gradle/gradle) - Used for compilation, building, testing and dependency management
- [Spring Boot (Webflux)](https://github.com/spring-projects/spring-boot) - For creating reactive REST APIs
- [Jackson](https://github.com/FasterXML/jackson-module-kotlin) - Provides a Kotlin module for automatic JSON serialization and deserialization
- [SpringDoc](https://github.com/springdoc/springdoc-openapi) - Provides Swagger documentation for REST APIs
- [Exposed](https://github.com/JetBrains/Exposed) - Lightweight Kotlin SQL library to interact with a database
- [H2database](https://github.com/h2database/h2database) - Provides an in-memory database for simple local testing
- [Liquibase](https://github.com/liquibase/liquibase) - Used to manage database schema changelogs

## Gradle best practices for Kotlin
[kotlinlang.org](https://kotlinlang.org/docs/gradle-best-practices.html)

### ✅ Use Kotlin DSL
This project uses Kotlin DSL instead of the traditional Groovy DSL by 
using **build.gradle.kts** files instead of **build.gradle** files.
The benefit here is that we do not need to use another programming 
language (Groovy) and can simply use Kotlin in our **build.gradle.kts** files.
This gives us the benefits of strict typing which lets IDEs provide 
better support for refactoring and auto-completion.

### ✅ Use a version catalog 

This project uses a version catalog 
[local.versions.toml](gradle/local.versions.toml)
which allows us to centralize dependency management. 
We can define versions, libraries, bundles and plugins here.
This enables us to use Gradle dependencies consistently across the entire project.

Dependencies can then be implemented in a specific **build.gradle.kts** file as such:
```kotlin
implementation(local.spring.boot.starter)
```

The Kotlinlang article says to name the version catalog **libs.versions.toml** 
but for this project it has been named **local.versions.toml**. The reason 
for this is that we can create a shared common version catalog which can 
be used across Gradle projects. Imagine that you are working on multiple 
similar Gradle projects with different purposes, but each project has some 
specific dependencies but also some dependencies in common. The dependencies 
that are common across projects could be placed in the shared version catalog 
while specific dependencies are placed in the local version catalog.

### ✅ Use local build cache

This project uses a local 
[build cache](https://docs.gradle.org/current/userguide/build_cache.html)
for Gradle which is a way to increase build performance because it will 
re-use outputs produced by previous builds. It will store build outputs 
locally and allow subsequent builds to fetch these outputs from the cache 
when it knows that the inputs have not changed. 
This means we can save time building

Gradle build cache is disabled by default so it has been enabled for this 
project by updating the root [gradle.properties](gradle.properties) file:
```properties
org.gradle.caching=true
```

This is enough to enable the local build cache
and by default, this will use a directory in the Gradle User Home 
to store build cache artifacts.

### ✅ Use configuration cache

This project uses 
[Gradle configuration cache](https://docs.gradle.org/current/userguide/configuration_cache.html)
and this will improve build performance by caching the result of the 
configuration phase and reusing this for subsequent builds. This means 
that Gradle tasks can be executed faster if nothing has been changed 
that affects the build configuration. If you update a **build.gradle.kts** 
file, the build configuration has been affected.

This is not enabled by default, so it is enabled by defining this in 
the root [gradle.properties](gradle.properties) file:
```properties
org.gradle.configuration-cache=true
org.gradle.configuration-cache.parallel=true
```

### ✅ Use modularization

This project uses modularization to create a 
[multi-project Gradle build](https://docs.gradle.org/current/userguide/multi_project_builds.html).
The benefit here is that we optimize build performance and structure our 
entire project in a meaningful way. This is more scalable as it is easier 
to grow a large project when you structure the code like this.

```
root
│─ build.gradle.kts
│─ settings.gradle.kts
│─ apps
│   └─ gateway
│       └─ build.gradle.kts
│   └─ provider
│       └─ build.gradle.kts
│─ modules
│   ├─ model
│   │   └─ build.gradle.kts
│   └─ persistence
│       └─ build.gradle.kts
```

This also allows us to specifically decide which Gradle dependencies will be used
for which subproject. Each subproject should only use exactly the dependencies
that they need.

Subprojects located under [apps](apps) are runnable, so this means we can 
run the **gateway** or **provider** project to spin up a Spring Boot REST API. 
We can add more subprojects under [apps](apps) to create additional 
runnable microservices.

Subprojects located under [modules](modules) are not independently runnable. 
The subprojects are used to structure code into various layers. The **model** 
subproject is the most inner layer and contains domain model classes and this 
subproject knows nothing about any of the other subprojects. The purpose of 
the **persistence** subproject is to manage the code responsible for 
interacting with the database. We can add more non-runnable subprojects 
under [modules](modules) if necessary. This could for example 
be a third-party integration.

---

#### Subproject with other subproject as dependency

The subprojects in this repository may use other subprojects as dependencies.

In our root [settings.gradle.kts](settings.gradle.kts) we have added:
```kotlin
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
```
Which allows us to add a subproject as a dependency in another subproject:

```kotlin
dependencies {
    implementation(projects.model)
}
```

This essentially allows us to define this structure:

```
gateway   
└─ model

provider  
│─ model  
└─ persistence

persistence  
└─ model

model has no dependencies
```
