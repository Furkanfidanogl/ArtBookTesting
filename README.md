# ArtBookTesting

<p align="left">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin" />
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Compose" />
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android" />
  <img src="https://img.shields.io/badge/Room-2C3E50?style=for-the-badge&logo=sqlite&logoColor=white" alt="Room" />
  <img src="https://img.shields.io/badge/Retrofit-4051B5?style=for-the-badge&logo=square&logoColor=white" alt="Retrofit" />
  <img src="https://img.shields.io/badge/Hilt%20DI-00897B?style=for-the-badge&logo=google&logoColor=white" alt="Hilt" />
  <img src="https://img.shields.io/badge/Pexels%20API-05A081?style=for-the-badge&logo=pexels&logoColor=white" alt="Pexels" />
</p>

A modern Android art collection app built with **Kotlin** and **Jetpack Compose** to practice layered architecture, local persistence, remote data access, dependency injection, and testing.

Users can search images via the **Pexels API**, save artworks locally, browse details, and manage items with individual, swipe, or bulk deletions.

---

## Features

- **Pexels API Search:** Remote image search with loading and error states
- **Local Persistence:** Offline storage with Room database
- **Reactive UI:** Automatic UI updates using Flow and StateFlow
- **Collection Management:** Add artworks, inspect details, swipe-to-delete, and bulk delete with confirmation
- **Shared State:** Shared state handling between Add and Search screens

---

## Architecture

The application follows a clean, decoupled layered architecture supporting the **Dependency Inversion Principle (DIP)**:

```text
┌─────────────────────────────────────────────────────────────┐
│                         Compose UI                          │
│          Declarative UI & Navigation Compose Flow           │
└──────────────────────────────┬──────────────────────────────┘
                               │ StateFlow (UI State) / Events
┌──────────────────────────────▼──────────────────────────────┐
│                        ArtViewModel                         │
│             Manages UI State & Data Operations              │
└──────────────────────────────┬──────────────────────────────┘
                               │ Inversion of Control
┌──────────────────────────────▼──────────────────────────────┐
│                  ArtRepositoryInterface                     │
│                   Repository Abstraction                    │
└──────────────────────────────┬──────────────────────────────┘
                               │
┌──────────────────────────────▼──────────────────────────────┐
│                  Repository (Implementation)                │
└──────────────┬──────────────────────────────┬───────────────┘
               │                              │
┌──────────────▼──────────────┐┌──────────────▼───────────────┐
│          Room DAO           ││         Retrofit API         │
│      (Local Database)       ││      (Remote Pexels API)     │
└─────────────────────────────┘└──────────────────────────────┘
```

`ArtRepositoryInterface` decouples the ViewModel from the concrete repository implementation:

- **Production:** `ArtRepositoryInterface` ──────► `Repository` (Room + Retrofit)
- **Testing:** `ArtRepositoryInterface` ──────► `FakeRepository` / Mockito Mock

This ensures the ViewModel remains decoupled, isolated, and completely testable without hitting disk or network layers.

---

## Tech Stack

| Category | Technologies |
| :--- | :--- |
| **Android & UI** | Kotlin, Jetpack Compose, Material 3, Navigation Compose, Kotlin Serialization, ViewModel, Coroutines, Flow / StateFlow |
| **Data & Network** | Room Database, Retrofit, OkHttp, Gson, Pexels API, Coil |
| **Dependency Injection** | Hilt |
| **Testing** | JUnit, Coroutines Test, Google Truth, Fake Repository, In-Memory Room, Hilt Testing (`@TestInstallIn`), Mockito Kotlin, Compose UI Testing |

---

## Testing Strategy

The test suite systematically isolates each layer of the application:

```text
               ▲
              / \     Compose UI Tests (Semantics, layout states, click callbacks)
             /───\
            /     \    Instrumented Tests (In-memory Room DAO & Hilt DI)
           /───────\
          /         \  JVM Unit Tests (ViewModel, Coroutines, FakeRepo, Mockito)
         ─────────────
```

### 1. ViewModel Unit Tests (JVM)
Tested in complete isolation using a `FakeRepository` and `TestDispatcher` to control coroutine execution:
- Insert artwork and observe StateFlow updates
- Query artwork by ID and observe full collection lists
- Delete single items or clear the entire collection

### 2. Room DAO Tests (Instrumented)
Executed using an **in-memory database** (`Room.inMemoryDatabaseBuilder`). Validates DAO insert/delete operations and Flow-based query results using an in-memory Room database without persistent disk side effects.

### 3. Hilt Integration Tests
Replaces the production `RepositoryModule` using `@TestInstallIn` and injects a `FakeRepository` through a dedicated test module.

### 4. Mockito Verification (JVM)
Used for mock-based behavior and interaction verification using `mock()`, `whenever()`, and `verify()`:

```kotlin
val repository: ArtRepositoryInterface = mock()

whenever(repository.getAllArts())
    .thenReturn(flowOf(listOf(art)))

val result = repository.getAllArts().first()
assertThat(result).contains(art)

repository.insertArt(art)
verify(repository).insertArt(art)
```

### 5. Compose UI Tests
Verified with `createAndroidComposeRule` and Compose Semantics matchers:
- **Empty State:** Verifies "No artwork yet" message visibility when the collection is empty
- **Content Display:** Asserts that artwork name and artist name are rendered correctly in the list
- **User Interactions:** Simulates item click and asserts that the `goToDetailScreen` callback triggers with the expected artwork ID

---

## Setup

This is a portfolio and practice project. To run it locally, add your Pexels API key to the root `local.properties` file:

```properties
PEXELS_API_KEY=YOUR_API_KEY
```

The key is read directly via `BuildConfig` and `local.properties` is excluded from Git by default.

---

## Data Flow Overview

```text
User Action ──► Compose UI ──► ViewModel ──► Repository ──► Room / API ──► StateFlow ──► UI Update
```
