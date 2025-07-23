# Falcon

Falcon is a modern Android application that demonstrates clean architecture and best practices for scalable app development. The project is designed to showcase:
- Clean separation of data, domain, and presentation layers
- Use of Jetpack Compose for UI
- Paging, GraphQL, and dependency injection
- Macrobenchmark and Baseline Profile integration for performance

## Description
Falcon is a sample app that displays a list of space launches (e.g., Space Falcon launches) with details for each launch. The app fetches data from a GraphQL API, supports pagination, and allows users to view detailed information about each launch, including rocket, mission, and site details.

The project is structured to be a reference for:
- Clean Architecture (data/domain/presentation separation)
- Modern Android development with Jetpack Compose
- Performance optimization using Baseline Profiles and Macrobenchmark
- Automated testing (unit, instrumented, and macrobenchmark tests)

## Tech Stack
- **Kotlin** (2.2+)
- **Jetpack Compose** (UI)
- **AndroidX** (core, lifecycle, navigation, paging, etc.)
- **Apollo GraphQL** (networking)
- **Koin** (dependency injection)
- **Coil** (image loading)
- **Timber** (logging)
- **Paging 3** (infinite scrolling)
- **JUnit, Robolectric, Mockito** (unit testing)
- **Espresso, UIAutomator** (instrumented testing)
- **androidx.benchmark, baselineprofile** (performance testing)
- **Detekt** (static code analysis)
- **Dependabot** (dependency updates)

## Features
- List and details of space launches
- Clean architecture with modular layers
- Jetpack Compose UI
- Pagination and error handling
- Performance profiling and optimization
- Comprehensive unit and instrumented tests

## Getting Started
1. Clone the repository
2. Open in Android Studio (Giraffe or newer recommended)
3. Build and run the app on a device or emulator (API 28+)
4. Run tests with `./gradlew test` and `./gradlew connectedAndroidTest`
5. Generate and verify Baseline Profiles with the `baselineprofile` module

## License
This project is licensed under the Apache 2.0 License. 