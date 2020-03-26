buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.6.1")
        classpath(kotlin("gradle-plugin", version = "1.3.71"))
    }
}

group = "com.temas.netlobby"
version = "0.1"


allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

