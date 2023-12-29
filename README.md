#  Wallet Sign in📱

Welcome to the repository. This project epitomizes modern Android development, incorporating Material3 design 🎨, Jetpack Compose 🖌️, and a clean architecture 🏗️ that adheres to SOLID principles for a scalable, maintainable, and robust application.

## Project Overview 👀

![Material](https://img.shields.io/badge/Material-Design-blue.svg?style=flat)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.4.3-brightgreen.svg?style=flat)
![Kotlin](https://img.shields.io/badge/kotlin-1.7.10-blue.svg?logo=kotlin)
![Firebase](https://img.shields.io/badge/Firebase-BOM%2032.4.0-orange)
![Koin](https://img.shields.io/badge/Koin-DI-green.svg?style=flat)
![Coil](https://img.shields.io/badge/Coil-Image%20Loading-blue.svg?style=flat)
![Lottie](https://img.shields.io/badge/Lottie-Animations-ff69b4.svg?style=flat)
![Clean Architecture](https://img.shields.io/badge/Clean%20Architecture-Enabled-green)
![SOLID](https://img.shields.io/badge/SOLID-Principles-blueviolet)


## Features ✨

### User Onboarding and Authentication 🔐

- **Registration**: Users can sign up seamlessly by entering personal data such as name, surname, email, and password.
- **Identification Photo**: As part of the security process, the app allows users to take a photo of their identification.
- **Success Screen**: A congratulatory screen that assures users of their successful account creation.

## Behind the Scenes 🛠️

### Architecture 🏗️

The application follows Clean Architecture guidelines, ensuring that the codebase is maintainable, scalable, and easily testable. The use of MVVM architecture plays a critical role in separating concerns, making the app robust and efficient.

### Firebase Integration 🔥

![Firebase](https://img.shields.io/badge/Firebase-orange?logo=firebase)

- **Firebase Authentication**: Ensures a secure and seamless sign-in experience for the users. It's implemented to manage user authentication, providing options like email/password login, social media login, and more.
  
- **Firebase Cloud Firestore**: Utilized as a scalable and flexible database for the app, storing and syncing user data and banking transactions in real-time across all client apps.

- **Firebase Storage**: Provides a robust solution for storing and serving user-generated content, such as profile pictures, documents, and other media.

- **Firebase Crashlytics**: Integrated to keep track of app stability and monitor and fix issues swiftly. This tool helps in identifying, prioritizing, and tracking the app's stability issues that affect the user experience.
  
## Getting Started 🚀

To clone and run this application, you'll need Git and Android Studio installed on your computer. From your command line:

```bash
# Clone this repository
$ git clone https://github.com/yourusername/wallet-project.git

# Go into the repository
$ cd wallet-project

# Install dependencies
$ Android Studio -> Sync Project with Gradle Files

# Run the app
```

## Dependencies 🧰

This project is built using a myriad of modern libraries and tools. Below is a list of the major frameworks and services integrated into the app:

- **Material3**: For a contemporary UI that adheres to the latest Material Design principles.
- **Jetpack Compose**: For a declarative UI that simplifies and accelerates UI development on Android.
- **Clean Architecture**: Ensuring separation of concerns and independence of different app layers.
- **SOLID Principles**: These principles are at the core of the app's design, ensuring each component is easily maintainable and upgradable.
- **Firebase**: For real-time data handling and authentication.

# Demo 🖼️

## 📸 Dark Mode Images

| Splash | Login| Register | Home (empty) |
|---------|---------|---------|---------|
| ![screenshot_splash_dark](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/4aff59bb-4a4d-41a5-8ba8-78059c4e6195) | ![screenshot_login_dark](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/2ca512e4-b056-49f0-84b3-247f5876f29c) | ![screenshot_register_dark](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/57adc5cd-0a28-4f49-af8a-297728ca698f) | ![screenshot_home_empty_dark](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/bbe2ba47-f456-424d-8291-0a2c25c769b9) |
| Success register | Home | Details | Logout |
| ![screenshot_success](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/9a6242eb-15ef-4eee-9e6c-e55b24b2469b) | ![screenshot_home_dark](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/3447ccd5-c588-4ce5-ad2b-4dccd2dbbeb2) | ![screenshot_details_dark](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/e698257f-e2ef-4cfd-b4c7-214f2f5fe709) | ![screenshot_logout_dark](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/c4008339-2bdc-400c-a987-9fb53d12215f) |


## 📸 Light Mode Images

| Splash | Login| Register | Home (empty) |
|---------|---------|---------|---------|
| ![screenshot_splash](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/235af294-4d7e-4587-a743-d6b8dbb6919e) | ![screenshot_login](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/e5151521-0ee9-4025-a7e3-9c3358d5bad2) | ![screenshot_register](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/9861f9c3-b065-4dd8-92af-5017f972dcab) | ![screenshot_home_empty](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/9db2a1fd-1424-447b-bfa2-08ddd989a920) |
| Success register | Home | Details | Logout |
| ![screenshot_success](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/9a6242eb-15ef-4eee-9e6c-e55b24b2469b) | ![screenshot_home](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/7c7c5f6a-7700-46bd-b9f1-d01f60e42836) | ![screenshot_details](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/54c48258-b294-4739-ab75-9bc136e94a2a) |![screenshot_logout](https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/c8a00ee4-5e49-42a1-be8d-0b19d03508b4) |


## 🎥 Demo

| Light demo | Dark demo |
|------------|-----------|
| <img src="https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/90a2f534-94c9-4df2-ac5f-3f41062fb682" width="400"> | <img src="https://github.com/lorenzosuarez/Stori-Challenge/assets/55887438/215fac7d-76f6-43bc-a679-288d2459f224" width="400"> |



Lorenzo Suarez
