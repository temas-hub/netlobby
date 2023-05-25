

plugins {
    kotlin("jvm") version "1.8.21"
    id("org.jetbrains.dokka") version "1.8.10"
    `maven-publish`
    signing

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

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().kotlin)
}

tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtmlMultiModule)
    from(tasks.dokkaHtmlMultiModule.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

val MAVEN_UPLOAD_USER: String by project
val MAVEN_UPLOAD_PWD: String by project
val IS_RELEASE: Boolean by project

publishing {
    repositories {
        maven {
            name = "MavenCentral"
            val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2"
            val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
            credentials {
                username = MAVEN_UPLOAD_USER
                password = MAVEN_UPLOAD_PWD
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["dokkaHtmlJar"])

            pom {
                name.set("Netlobby")
                description.set("Client-server library for multiplayer games")
                url.set("https://github.com/temas-hub/netlobby")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.tx")
                    }
                }
                developers {
                    developer {
                        name.set("Artem Zhdanov")
                        email.set("temas_coder@yahoo.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/temas-hub/netlobby.git")
                    developerConnection.set("scm:git:https://github.com/temas-hub/netlobby.git")
                    url.set("https://github.com/temas-hub/netlobby")
                }

            }
        }
    }
}

if (IS_RELEASE) {
    signing {
        val PGP_SIGNING_KEY: String? by project
        val PGP_SIGNING_PASSWORD: String? by project
        useInMemoryPgpKeys(PGP_SIGNING_KEY, PGP_SIGNING_PASSWORD ?: "")
        sign(publishing.publications["mavenJava"])
    }
}




