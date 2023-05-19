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
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("io.netty:netty-all:4.1.86.Final")
}

application {
    mainClass.set("com.temas.netlobby.core.bootstrap.ServerBuilderKt")
}