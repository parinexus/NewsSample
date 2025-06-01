# NewsSample

NewsSample is an Android news application built using a **Clean Architecture** approach. The project is organized into four primary Gradle modules—`app`, `data`, `domain`, and `presentation`—each responsible for a distinct layer of the application:

* **app**: Android entry point (UI host, application setup, and dependency injection initialization).
* **data**: Data‐layer implementation (API service, local data source, mappers, repositories).
* **domain**: Business‐logic layer (use cases, domain models, repository interfaces, state management).
* **presentation**: UI layer built using Jetpack Compose (screens, view models, navigation, theming, and UI components).

This README explains how to clone, build, and run the project, and it provides an overview of each module’s responsibility and structure.

---

## Table of Contents

* [Features](#features)
* [Architecture Overview](#architecture-overview)

    * [Clean Architecture Layers](#clean-architecture-layers)
    * [Dependency Injection](#dependency-injection)
* [Project Structure](#project-structure)

    * [`app/`](#app)
    * [`data/`](#data)
    * [`domain/`](#domain)
    * [`presentation/`](#presentation)
* [Prerequisites](#prerequisites)
* [Getting Started](#getting-started)

    * [Clone the Repository](#clone-the-repository)
* [Module Details](#module-details)

    * [app](#app-module-details)
    * [data](#data-module-details)
    * [domain](#domain-module-details)
    * [presentation](#presentation-module-details)
* [Testing](#testing)
* [Contributing](#contributing)
* [License](#license)

---

## Features

* Fetch and display top news headlines from a remote API.
* Clean Architecture with separation of concerns:

    * **Presentation** layer written in Jetpack Compose.
    * **Domain** layer encapsulating business logic via use cases.
    * **Data** layer handling remote (Retrofit) and local (Room, SharedPreferences, etc.) data sources.
* Dependency Injection via Hilt (Dagger).
* Offline caching support (Room database) and data mappers.
* Navigation between screens (Home, Search, Details, Favorites).
* Search functionality to filter news by keyword.
* Favorite articles feature backed by a local data store.
* Theming (light/dark) and utility functions for consistent UI.

---

## Architecture Overview

### Clean Architecture Layers

1. **Presentation** (Jetpack Compose)

    * Responsible for UI implementation: composable screens, view models, and navigation.
    * Observes UI state (`StateFlow`) exposed by ViewModels.
    * Does not depend on Android framework classes outside of Compose, ViewModel, and Hilt.

2. **Domain**

    * Contains pure business logic:

        * **Use Cases** (`ArticleUseCase`, etc.) that orchestrate data retrieval and manipulation.
        * **Domain Models** (`Article`, etc.) and repository interfaces (`RemoteRepository`, `LocalRepository`, `FavoriteRepository`).
        * **State** classes (e.g., `Resource`) to represent loading, success, and error states.
    * Does not depend on any Android framework or implementation details.

3. **Data**

    * Implements domain‐defined repository interfaces.

        * **RemoteRepositoryImpl** uses Retrofit to fetch data from a remote API (`ApiService`).
        * **LocalRepositoryImpl** handles local persistence (Room, SharedPreferences, or another storage).
    * Contains data models, mappers to convert between remote models and domain models, and data constants.
    * Exposes repository implementations to the domain layer via dependency injection.

4. **App**

    * Android application class (`NewsApp`) where Hilt is initialized (`@HiltAndroidApp`).
    * Hosts the `MainActivity` which sets up the navigation host for the Compose UI.
    * Configures application‐wide dependencies: Retrofit client, Room database, repository bindings, and use case bindings via Hilt modules (in `di/`).

### Dependency Injection

* **Hilt** is used to manage object graphs across the entire application.

    * **Modules** inside `app/src/main/java/newssample/di` define how to provide:

        * `NetworkModule`: Retrofit instance and API service.
        * `DatabaseModule`: Room database and DAO instances.
        * `RepositoryModule`: Binds interface types (`RemoteRepository`, `LocalRepository`, `FavoriteRepository`) to their implementations.
        * `UseCaseModule`: Provides domain‐level use cases.

---

## Project Structure

Each top‐level directory corresponds to a Gradle module. Below is an overview of the module tables and key packages.

```
Root
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/newssample/
│   │   │   │   ├── di/
│   │   │   │   │   ├── DatabaseModule.kt
│   │   │   │   │   ├── NetworkModule.kt
│   │   │   │   │   ├── RepositoryModule.kt
│   │   │   │   │   └── UseCaseModule.kt
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── NewsApp.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/  (if any XML is used alongside Compose)
│   │   │   │   └── values/
│   │   │   └── AndroidManifest.xml
│   │   └── test/ 
│   ├── .gitignore
│   ├── build.gradle.kts
│   └── proguard-rules.pro
│
├── data/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/data/
│   │   │   │   ├── constants/       (API endpoints, keys, timeouts)
│   │   │   │   ├── local/           (Room entities, DAO, shared prefs)
│   │   │   │   ├── mapper/          (DTO ↔ Domain mappers)
│   │   │   │   ├── model/           (Data models/DTOs)
│   │   │   │   ├── remote/          (ApiService interfaces, Retrofit definitions)
│   │   │   │   │   └── ApiService.kt
│   │   │   │   └── repository/
│   │   │   │       ├── LocalRepositoryImpl.kt
│   │   │   │       └── RemoteRepositoryImpl.kt
│   │   │   └── AndroidManifest.xml  (usually empty or merged)
│   │   └── test/
│   ├── .gitignore
│   ├── build.gradle.kts
│   └── proguard-rules.pro
│
├── domain/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/domain/
│   │   │   │   ├── model/           (Domain models, e.g., `Article.kt`)
│   │   │   │   ├── repository/      (Repository interfaces)
│   │   │   │   │   ├── FavoriteRepository.kt
│   │   │   │   │   ├── LocalRepository.kt
│   │   │   │   │   └── RemoteRepository.kt
│   │   │   │   ├── state/           (Resource.kt to wrap loading/success/error)
│   │   │   │   └── usecase/         (ArticleUseCase.kt, etc.)
│   │   │   └── AndroidManifest.xml  (empty or merged)
│   │   └── test/
│   ├── .gitignore
│   ├── build.gradle.kts
│   └── proguard-rules.pro
│
├── presentation/
│   ├── src/
│   │   ├── androidTest/
│   │   ├── main/
│   │   │   ├── java/presentation/
│   │   │   │   ├── components/     (Composable screens and UI components)
│   │   │   │   │   ├── FavoriteScreen.kt
│   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   ├── NewsDetailScreen.kt
│   │   │   │   │   ├── NewsItem.kt
│   │   │   │   │   └── SearchScreen.kt
│   │   │   │   ├── mapper/         (Mapper.kt to convert domain models to UI models)
│   │   │   │   ├── model/          (UI state/data classes, e.g., `NewsUiModel.kt`, `FavoriteUiModel.kt`)
│   │   │   │   ├── navigation/     (NavGraph, routes, deep links)
│   │   │   │   ├── theme/          (Color schemes, typography, shapes, `Theme.kt`)
│   │   │   │   ├── utils/          (Extension functions, helpers)
│   │   │   │   └── viewModel/      (ViewModels that expose UI state to composables)
│   │   │   ├── res/                (Compose uses mostly no XML; may contain drawables, strings, colors)
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   ├── .gitignore
│   ├── build.gradle.kts
│   └── proguard-rules.pro
│
├── build.gradle.kts        (root Gradle build script aggregating modules)  
├── settings.gradle.kts     (includes `:app`, `:data`, `:domain`, `:presentation`)  
├── gradle.properties       (project-wide Gradle properties)  
├── gradlew                  (Unix Gradle wrapper)  
├── gradlew.bat             (Windows Gradle wrapper)  
└── local.properties        (contains local secrets, e.g., API keys)  
```

---

## Prerequisites

Before cloning and building NewsSample, make sure you have:

* **Android Studio Arctic Fox (2020.3.1) or later** installed.
* **Android SDK** with at least **API Level 21 (Lollipop)**.
* **JDK 17**.
* A **News API key** (e.g., from [NewsAPI](https://newsdata.io/)) or a compatible endpoint—see “Configure API Keys” below.
* Internet access to fetch dependencies and to query the news API at runtime.

---

## Getting Started

### Clone the Repository

Open a terminal and run:

```bash
git clone https://github.com/parinexus/NewsSample.git
cd NewsSample
```

The Home screen should load, fetch the latest top headlines, and display them in a Compose-based `LazyColumn`.

---

## Module Details

Below is a more detailed look at what each module contains and how they collaborate.

### app (Android Module)

* **Purpose**: Entry point for the Android application. Hosts the `MainActivity` and initializes Hilt.

* **Key Files**:

    * `NewsApp.kt` – Annotated with `@HiltAndroidApp`, it initializes Hilt.
    * `MainActivity.kt` – Hosts the Compose `NavHost` for navigation between Home, Search, Details, and Favorites screens.
    * `di/NetworkModule.kt` – Provides Retrofit, `OkHttpClient`, and `ApiService` instances.
    * `di/DatabaseModule.kt` – Configures and provides the Room database, DAOs, and local data store.
    * `di/RepositoryModule.kt` – Binds domain repository interfaces (`RemoteRepository`, `LocalRepository`, `FavoriteRepository`) to their concrete implementations in `data`.
    * `di/UseCaseModule.kt` – Provides domain use cases (e.g., `ArticleUseCase`) to be injected into ViewModels.

* **Build Script**:

    * The `build.gradle.kts` in `app/` includes Hilt Gradle plugin, Compose dependencies, and sets the `applicationId` (e.g., `com.parinexus.newssample`).
    * Contains a `buildConfigField("String", "API_KEY", "\"${properties["NEWS_API_KEY"] ?: ""}\"")` that injects your API key.

### data (Java/Kotlin Library Module)

* **Purpose**: Implements the repository interfaces defined in `domain`. Contains all classes that know how to fetch data from remote and local sources.

* **Key Packages**:

    * `data.constants`

        * Houses constant values such as base URLs, endpoints, timeouts, and static parameters used by Retrofit.
    * `data.remote`

        * `ApiService.kt` – Retrofit interface with functions like `suspend fun getTopHeadlines(@Query("q") query: String, @Query("apiKey") apiKey: String): Response<NewsResponse>`.
        * DTOs (e.g., `NewsResponseDto`, `ArticleDto`) to match the JSON schema returned by the News API.
    * `data.local`

        * Room entity classes (e.g., `ArticleEntity.kt`) and DAO interfaces (e.g., `ArticleDao.kt`).
        * Possibly a SharedPreferences helper if some simple key‐value pairs are needed.
    * `data.mapper`

        * Converter functions that map:

            * **Remote DTO → Domain Model**
            * **Local Entity → Domain Model**
            * **Domain Model → Local Entity**
    * `data.model`

        * Plain data classes representing DTOs for Retrofit responses (e.g., `SourceDto`, `ArticleDto`).
    * `data.repository`

        * `RemoteRepositoryImpl.kt` – Implements `RemoteRepository` from the `domain` module. Uses `ApiService` to fetch remote data, maps DTOs to domain models, and returns domain results (wrapped in `Resource<T>`).
        * `LocalRepositoryImpl.kt` – Implements `LocalRepository` and `FavoriteRepository`. Handles Room database operations (insert, delete, query favorites, etc.).

* **Build Script**:

    * Declares dependencies on Retrofit, Room, Hilt’s AndroidX extensions (for injecting the DAO), and Kotlin coroutines.

### domain (Java/Kotlin Library Module)

* **Purpose**: Defines the core business logic and entities without any Android or external‐library dependencies.

* **Key Packages**:

    * `domain.model`

        * Contains pure Kotlin data classes like `Article`, `Source`, and `Favorite`. These models are agnostic to Retrofit or Room.
    * `domain.repository`

        * Interfaces that the data module must implement:

            * `RemoteRepository` – Defines functions like `suspend fun fetchTopHeadlines(query: String): Resource<List<Article>>`.
            * `LocalRepository` – Defines functions for caching or retrieving locally saved articles.
            * `FavoriteRepository` – Defines functions to add, remove, and list favorite articles.
    * `domain.state`

        * `Resource.kt` – Sealed class representing loading, success, or error states. Example:

          ```kotlin
          sealed class Resource<out T> {
              data object Loading : Resource<Nothing>()
              data class Success<T>(val data: T) : Resource<T>()
              data class Error(val message: String, val cause: Throwable? = null) : Resource<Nothing>()
          }
          ```
    * `domain.usecase`

        * `ArticleUseCase.kt` – Orchestrates calls to `RemoteRepository` and `LocalRepository` to:

            * Fetch news from the API.
            * Save or retrieve cached articles if offline.
            * Manage favorites via `FavoriteRepository`.
        * You can add additional use cases (e.g., `SearchArticlesUseCase`, `GetFavoriteArticlesUseCase`) as needed.

* **Build Script**:

    * Typically only includes Kotlin stdlib and coroutines dependencies. It does **not** depend on AndroidX or UI libraries.

### presentation (Android Library Module)

* **Purpose**: Implements the UI layer using Jetpack Compose. Contains all Compose screens, UI components, navigation setup, view models, and theming. Depends on the `domain` module to execute use cases and expose data/state to the UI.

* **Key Packages**:

    * `presentation.components`

        * Composable functions for each screen:

            * `HomeScreen.kt` – Shows a list of news articles fetched from the API. Injects `NewsViewModel` to observe `StateFlow<List<ArticleUiModel>>`.
            * `SearchScreen.kt` – Provides a search bar and displays filtered results in real time.
            * `NewsDetailScreen.kt` – Displays details of a single article, including image, title, author, content, and a “Read Full Article” button (opens a browser).
            * `FavoriteScreen.kt` – Displays a list of user‐favorited articles, with the ability to remove favorites.
            * `NewsItem.kt` – Reusable composable for rendering each article’s thumbnail, title, and snippet in a `LazyColumn`.
    * `presentation.mapper`

        * `Mapper.kt` – Converts domain models (`Article`, `Source`) to UI‐specific models (`ArticleUiModel`, etc.) used by Compose.
    * `presentation.model`

        * Defines UI state classes or sealed classes that represent the screen state (e.g., `HomeUiState`, `SearchUiState`, `FavoriteUiState`).
    * `presentation.navigation`

        * `NavGraph.kt` or similar Compose‐based navigation graph. Defines navigation routes (e.g., “home”, “search”, “detail/{articleId}”, “favorites”).
    * `presentation.theme`

        * `Theme.kt`, `Color.kt`, `Typography.kt`, `Shape.kt` – Compose Material theming setup.
    * `presentation.utils`

        * Utility functions/extensions for Compose (e.g., network checks, date formatting, common modifiers).
    * `presentation.viewModel`

        * `NewsViewModel.kt` – Injected with `ArticleUseCase` by Hilt. Exposes UI state via `StateFlow`. Handles events such as “load top headlines,” “search query,” “add/remove favorite.”
        * Additional view models for search and favorites screens if needed.

* **Build Script**:

    * Includes Compose BOM, Material3, Hilt‐for‐Compose dependencies, lifecycle, and navigation-compose.
    * Uses `kotlin-kapt` or `kotlin-parcelize` if necessary for model passing.

---

## Testing

Each module contains its own `test` source set. Unit tests can reside in:

* `app/src/test/` (for any Activity/Instrumentation‐independent logic in the app module).
* `data/src/test/` (for repository tests, mapper tests, etc.). Use Mockito or MockK to mock `ApiService` or DAOs.
* `presentation/src/androidTest/` (for Compose UI tests with `composeTestRule`).
* `presentation/src/test/` (ViewModel unit tests with coroutine test dispatcher and fake `ArticleUseCase`).

To run all unit tests:

```bash
./gradlew test
```

To run Android instrumentation tests (Compose UI tests):

```bash
./gradlew connectedAndroidTest
```

---

## Contributing

We welcome contributions! If you find a bug or want to propose a new feature, please:

1. **Fork** this repository.
2. **Create a new branch** with a descriptive name (e.g., `feature/add-pagination`).
3. **Implement** your changes, and **add tests** where appropriate.
4. **Ensure** all existing tests pass (`./gradlew test`).
5. **Submit** a Pull Request describing your changes in detail.

Before submitting, verify that:

* You’ve followed the existing code style (Kotlin conventions, 4‐space indent, etc.).
* Hilt modules and dependency graphs remain consistent.
* Compose screens compile and display correctly on both light and dark themes.

---

## License

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for full details.

```
MIT License

Copyright (c) 2025 parinexus

Permission is hereby granted, free of charge, to any person obtaining a copy...
```

---