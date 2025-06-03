# 📰 NewsSample

**NewsSample** is a modular Android news app built using **Clean Architecture**, **Jetpack Compose**, and **Hilt**. It fetches and displays top news headlines, supports search, article favoriting, and now includes **favorite category tracking**.

---

## 🧱 Architecture Overview

The project follows a four-layer Clean Architecture:

| Module         | Responsibility                                                      |
| -------------- | ------------------------------------------------------------------- |
| `app`          | App startup, dependency injection, main activity                    |
| `data`         | Data source implementations (Retrofit, Room), mappers, repositories |
| `domain`       | Use cases, domain models, business logic                            |
| `presentation` | Jetpack Compose UI, navigation, state management, view models       |

---

## ✨ Features

* Fetch latest top headlines from a remote API
* Search news by keyword
* Mark/unmark articles as favorites
* Browse saved favorite articles offline
* Track favorite news categories 🔖
* Compose UI with light/dark theme
* Hilt-powered DI with fully decoupled layers

---

## 🔧 Prerequisites

* Android Studio **Arctic Fox (2020.3.1+)**
* Android SDK **API 21+**
* JDK **17**
* News API key (add to `build.gradle`):

  ```
  NEWS_API_KEY=your_api_key
  ```

---

## 🚀 Getting Started

```bash
git clone https://github.com/parinexus/NewsSample.git
cd NewsSample
./gradlew installDebug
```

---

## 🆕 Favorite Categories

Users can now **add and manage preferred news categories**. These are stored locally using DataStore and used to filter or highlight relevant content. The feature follows the same Clean Architecture principles as the rest of the app and is encapsulated in its own repository and use case.

---

## 🧪 Testing

Run unit tests:

```bash
./gradlew test
```

Run Compose UI tests:

```bash
./gradlew connectedAndroidTest
```

---

## 🤝 Contributing

1. Fork the repo
2. Create a feature branch
3. Make changes with tests
4. Open a PR

---

## 📄 License

MIT License © 2025 parinexus

---

Let me know if you’d like a Markdown version exported, or links/badges (build, coverage, etc.) added.
