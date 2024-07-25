
<div align="center">
    <h1>Movie App</h1>
    <img src="app/src/main/res/drawable/logo.png" alt="Logo" width="200"/>
</div>

A movie application that allows users to view popular, upcoming, and highly-rated films, as well as track movies they are currently watching. The app also includes a wishlist feature for saving favorite movies.

## GIF
![Example](/app/src/main/res/drawable/gif.gif)

## Description

This project is designed to help users discover and keep track of their favorite films. The app fetches data from movie databases to provide up-to-date information on the latest and most popular movies. Users can also save movies to a wishlist for easy access later. The app offers a user-friendly interface built with Kotlin and Jetpack Compose for a seamless experience.

## Getting Started

### Dependencies

* Kotlin 1.9.0
* Gradle 8.2.2
* Java 1.8
* Android Studio (latest version recommended)
* [TMDB API](https://developer.themoviedb.org/docs/getting-started) for get movie
* [Jetpack Compose](https://developer.android.com/jetpack/compose) for UI
* [Dagger-Hilt](https://dagger.dev/hilt/)for dependency injection
* [Room](https://developer.android.com/training/data-storage/room) for caching data

### Installing

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/NovikFeed/MoviesApp.git
   ```
2. Navigate to the project directory:
   ```bash
   cd MovieApp
   ```
3. Open the project in Android Studio.
4. Sync the project with Gradle files by clicking on "Sync Project with Gradle Files" in Android Studio.

### Executing Program

1. Build and run the app by selecting the desired emulator or connected device.
2. Click the "Run" button in Android Studio or use the following command in your terminal:
   ```bash
   ./gradlew assembleDebug
   ```
3. Once the app is installed on your device or emulator, you can start exploring the features.

## Help

If you encounter any issues, please refer to the following command to check the app's log output for troubleshooting:
```bash
adb logcat
```

## Version History

* 0.2
    * Various bug fixes and optimizations
    * Added more detailed movie information
    * See [commit change](https://github.com/NovikFeed/MoviesApp/commits/develop) or See [release history](https://github.com/NovikFeed/MoviesApp/commits/)
* 0.1
    * Initial Release
    * Basic features implemented

