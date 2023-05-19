plugins {
    kotlin("jvm")
    application
}

group = "com.temas.netlobby"
version = "0.2"

val koinVersion : String by rootProject

dependencies {
    implementation(project(":core"))
    implementation("io.insert-koin:koin-core:$koinVersion")
    testImplementation("io.insert-koin:koin-test:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit4:$koinVersion")

}

application {
    mainClass.set("com.temas.netlobby.core.bootstrap.ClientBuilderKt")
}