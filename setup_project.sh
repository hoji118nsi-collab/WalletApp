#!/bin/bash
set -e

# ایجاد Gradle Wrapper
gradle wrapper --gradle-version 8.4 || echo "Gradle احتمالا نصب نیست، ادامه می‌دهیم..."

# فایل settings.gradle
cat > settings.gradle << SETTINGS
rootProject.name = "WalletApp"
include(":app")
SETTINGS

# فایل build.gradle ریشه
cat > build.gradle << ROOTBUILD
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.4.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
ROOTBUILD

# فایل app/build.gradle
mkdir -p app
cat > app/build.gradle << APPBUILD
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'com.example.walletapp'
    compileSdk 34

    defaultConfig {
        applicationId "com.example.walletapp"
        minSdk 24
        targetSdk 34
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding true
    }
}

dependencies {
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.appcompat:appcompat:1.7.0'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.2.0'
}
APPBUILD

# فایل AndroidManifest.xml
mkdir -p app/src/main
cat > app/src/main/AndroidManifest.xml << MANIFEST
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.walletapp">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="کیف پول شایان"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AppCompat.Light.NoActionBar">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
MANIFEST

echo "Setup کامل شد! حالا می‌توانید ./gradlew :app:assembleDebug را اجرا کنید."
