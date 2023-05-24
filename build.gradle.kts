

plugins {
    kotlin("jvm") version "1.8.21"
    id("org.jetbrains.dokka") version "1.8.10"

    /**
     * Use `apply false` in the top-level build.gradle file to add a Gradle
     * plugin as a build dependency but not apply it to the current (root)
     * project. Don't use `apply false` in sub-projects. For more information,
     * see Applying external plugins with same version to subprojects.
     */

    id("com.android.application") version "7.4.1" apply false
    id("com.android.library") version "7.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
}

group = "com.temas.netlobby"
version = "0.2"

tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtmlMultiModule)
    from(tasks.dokkaHtmlMultiModule.flatMap { it.outputDirectory })
    archiveClassifier.set("html-docs")
}



