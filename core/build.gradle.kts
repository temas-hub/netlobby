plugins {
    kotlin("jvm")
}

val koinVersion : String by rootProject

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.koin:koin-core:$koinVersion")
    implementation("io.netty:netty-all:4.1.48.Final")
    testImplementation("org.koin:koin-test:$koinVersion")
}
