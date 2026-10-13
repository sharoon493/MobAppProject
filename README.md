# OnDonate — Online Donation Platform

OnDonate is an Android mobile application concept designed to make donating useful items easier. Instead of limiting donations to money, the platform helps people raise requests for needed items and allows others to donate items such as clothes, shoes, bags, and books.

This project was developed as a semester project for **CSCS 468 — Mobile Application Development** at **Forman Christian College (A Chartered University)**.

## Features

- User sign-up and sign-in
- Browse donation requests and available donations
- Create a request for an item
- Post an item for donation
- Search using keywords
- View item/request details
- View user activities
- Select images using Android intents
- Send email using an implicit intent
- Store and retrieve application data using Firebase
- Upload images to Firebase Storage
- Day/Night theme support
- Image loading with Glide
- Landscape layout variations

## Technologies and Android Components

- **Firebase Authentication** — user authentication
- **Firebase Realtime Database** — storing and retrieving data
- **Firebase Storage** — uploading images
- **RecyclerView** and **ListView** — displaying lists
- **Spinner** and dialogs — selecting options
- **Menus** and a customized ActionBar search
- **SharedPreferences** — storing local preferences
- **Implicit Intents** — choosing/taking images and opening email
- **Glide** — loading images
- **Theme Editor** — Day/Night themes

## Project Status

The semester-project implementation covers the features listed above. The original project report identified the following as planned but not completed due to time constraints:

- Facebook login
- Sharing on Facebook
- Google Maps integration

These features should be considered incomplete unless they have since been implemented in the repository.

## Getting Started

1. Clone this repository:
   ```bash
   git clone https://github.com/sharoon493/MobAppProject.git
   ```
2. Open the project in **Android Studio**.
3. Allow Android Studio to sync the Gradle project and download required dependencies.
4. Configure Firebase for your own Firebase project if the repository does not already include a valid configuration:
   - Create a project in the Firebase Console.
   - Register the Android application using its package name.
   - Add the appropriate `google-services.json` file.
   - Enable the Firebase services used by the app, including Authentication, Realtime Database, and Storage.
   - Review and configure Firebase security rules before using real user data.
5. Build and run the app on an Android emulator or compatible Android device.

> **Note:** Exact Android Studio, Gradle, Java/Kotlin, and Firebase setup requirements depend on the versions and configuration in the repository. Check the project files for the versions actually used.

## Screenshots

The original project report includes screenshots of the application, including:

- Launcher icon
- Sign-up and sign-in screens
- Requests and donations tabs
- Creating a request and donating an item
- Search and detail screens
- User activity and About screens
- Landscape layouts

Add screenshots to this repository (for example, in a `screenshots/` folder) and link them here if you want them displayed on the GitHub project page.

Example:

```markdown
![Sign In](screenshots/sign-in.png)
```

## Team

- **Sharoon Nasim** — 19-10614
- **Joial Daniyal** — 19-10589

**Course:** CSCS 468 — Mobile Application Development  
**Institution:** Forman Christian College (A Chartered University)  
**Instructor:** Dr. Nazim Ashraf  
**Semester:** Fall 2018

## Repository

[github.com/sharoon493/MobAppProject](https://github.com/sharoon493/MobAppProject)

## Contributing

Contributions, bug reports, and suggestions are welcome. Please open an issue to discuss significant changes before submitting a pull request.

## License

No license was specified in the project report. Unless a license file is added to this repository, reuse and redistribution are not automatically permitted. Add a license that reflects how you want others to use the project.
