plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35 //Eta age 34 chilo exoplayer er support er jonno 35 e nite hoche

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        //now
        multiDexEnabled = true;

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
//
//    buildFeatures  {
//        viewBinding = true
//    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //for recconnect same firebase project in one app 
    implementation ("com.google.firebase:firebase-analytics")


    implementation("com.google.firebase:firebase-analytics:22.1.0")

    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.google.firebase:firebase-firestore:25.1.0")
    //for android pdf viewer
//    implementation ("com.github.barteksc:android-pdf-viewer:2.8.2")
    implementation ("com.joanzapata.pdfview:android-pdfview:1.0.4@aar")

//        implementation ("com.github.barteksc:android-pdf-viewer:3.2.0-beta.1") // Use a newer version



    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")


    //multidex
    implementation("androidx.multidex:multidex:2.0.1")


    //VideSection Exoplayer Library
    implementation ("androidx.media3:media3-exoplayer:1.5.1")
    implementation ("androidx.media3:media3-exoplayer-dash:1.5.1")
    implementation ("androidx.media3:media3-ui:1.5.1")
    //VideSection Exoplayer Library

    //glide image
//    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
    implementation ("com.github.bumptech.glide:glide:4.16.0")


    // cloudinary database dependency
    implementation ("com.cloudinary:cloudinary-android:3.0.2")

    //splash screen for android 12
    implementation("androidx.core:core-splashscreen:1.0.0")

//    //resonsive
//    implementation ("com.intuit.sdp:sdp-android:1.1.1")
//
//    //resonsive
//    implementation ("com.intuit.sdp:ssp-android:1.1.1")








}