buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.1")
        classpath(kotlin("gradle-plugin", version = "1.5.0")) // <- change kotlin version here
    }
}

group = "com.temas.netlobby"
version = "0.2"

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}


