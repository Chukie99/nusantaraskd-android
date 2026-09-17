# Nusantara SKD Android Project

Package: `com.nusantaraskd.app`
Min SDK: 26
Target SDK: 34

## Struktur Folder Final
```
nusantaraskd/
├── app/src/main/
│   ├── AndroidManifest.xml
│   ├── java/com/nusantaraskd/
│   │   ├── app/
│   │   │   ├── MainActivity.kt
│   │   │   └── NusantaraApp.kt
│   │   ├── data/room/
│   │   │   └── EntitiesAndDatabase.kt
│   │   └── ui/
│   │       ├── screen/
│   │       │   ├── NavGraph.kt
│   │       │   └── SplashScreen.kt
│   │       └── theme/
│   │           ├── Color.kt
│   │           ├── Theme.kt
│   │           └── Type.kt
│   └── res/
│       ├── mipmap/
│       │   ├── ic_launcher.xml
│       │   ├── ic_launcher_round.xml
│       │   └── ic_launcher_foreground.xml
│       └── values/
│           └── strings.xml
├── build.gradle (project)
├── app/build.gradle
├── settings.gradle
├── gradle.properties
└── gradlew
```

## Cara Build
```bash
cd nusantaraskd
gradle assembleDebug
```

## Catatan
- Install Android Studio atau Gradle terpisah untuk build
- Set `sdk.dir` di `local.properties` ke lokasi Android SDK
- Google Services (Firebase) perlu manual setup dengan `google-services.json`

PHASE 5.1 ANDROID PROJECT SKELETON COMPLETE — WAITING FOR HUMAN REVIEW.