plugins {
    kotlin("jvm")
}

val koinVersion : String by rootProject

dependencies {
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.netty:netty-all:4.1.48.Final")
    implementation("de.ruedigermoeller:fst:2.56")
    testImplementation("io.insert-koin:koin-test:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit4:$koinVersion")
}
