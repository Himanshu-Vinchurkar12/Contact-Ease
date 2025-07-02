# 📱 ContactEase – Android Contact Manager App

![Build](https://img.shields.io/badge/build-passing-brightgreen)
![Platform](https://img.shields.io/badge/platform-Android-blue)
![Jetpack Compose](https://img.shields.io/badge/Jetpack--Compose-UI%20Framework-orange)
![License](https://img.shields.io/github/license/yourusername/contactease)

Welcome to **ContactEase** – My first Android application, built with ❤️ as I step into the world of app development.

This app is a **Contact Manager** with intuitive UI, clean architecture, gesture interactions, and cloud integration – built using modern tools like **Kotlin**, **Jetpack Compose**, **Room DB**, and **MVVM architecture**. Fully responsive across all device sizes.

---

## 🚀 Features

- 🏠 **Home Screen**
  - Displays all contacts with **total count**
- 🔍 **Search Contacts**
  - Real-time filtering using a **search bar**
- 🔃 **Sort Functionality**
  - Sort contacts by **name** or **date created**
- 👤 **Detail Screen**
  - Tap a contact to view full information
- ✏️ **Edit Contact**
  - Long-press to open **EditScreen**
- 📞 **Swipe-to-Call**
  - Swipe left to initiate a **direct phone call**
- ☁️ **Cloud Sync**
  - Data is stored locally with optional **cloud storage integration**

---

## 📸 Screenshots

> Add your actual screenshots in a `/screenshots` folder or upload via Imgur and link them here.

| Home Screen | Detail Screen | Edit Screen | Swipe-to-Call |
|-------------|---------------|-------------|----------------|
| ![home](screenshots/home_screen.png) | ![detail](screenshots/detail_screen.png) | ![edit](screenshots/edit_screen.png) | ![swipe](screenshots/swipe_call.png) |

---

## 🎥 App Preview (GIF)

> Upload a screen recording or GIF here.

![app-preview](screenshots/app_preview.gif)

---

## 🧰 Tech Stack

- **Kotlin** – Programming language
- **Jetpack Compose** – Modern declarative UI
- **Room Database (MySQL)** – Local data persistence
- **MVVM Architecture** – Clean, scalable codebase
- **LiveData & ViewModel** – State management
- **Cloud Storage Integration** – For future-ready sync
- **Responsive UI** – Works on all screen sizes

---

## 🛠 Installation Guide

### Prerequisites:
- Android Studio Hedgehog or later
- Minimum SDK: 24 (Android 7.0)
- Kotlin 1.9+
- Internet connection (for cloud sync)

### Steps:

1. **Clone the Repository**

```bash
git clone https://github.com/yourusername/contactease.git



com.example.contactease
├── data/           # Room database + DAO
├── model/          # Data classes
├── repository/     # Repository layer
├── ui/
│   ├── screens/    # Home, Detail, Edit, etc.
│   └── components/ # Reusable composables
├── viewmodel/      # ViewModel classes
└── utils/          # Constants, helpers

