plugins {
    java
    kotlin("jvm") version "1.3.72" apply false
    `maven-publish`
}

version = "1.0-SNAPSHOT"

subprojects {

    group = "rs.emulator"

    version = "1.0-SNAPSHOT"

    repositories {
        jcenter()
        mavenCentral()
        maven {
            setUrl("http://localhost:8081/nexus/content/groups/public")
        }
    }

    apply(plugin = "kotlin")

    apply(plugin = "maven-publish")

    dependencies {

        implementation(kotlin("stdlib"))

        implementation("org.slf4j:slf4j-simple:2.0.0-alpha1")

        implementation("org.slf4j:slf4j-api:2.0.0-alpha1")

        implementation("io.github.microutils:kotlin-logging:1.7.9")

        implementation("com.google.inject:guice:4.2.3")

        implementation("net.onedaybeard.artemis:artemis-odb:2.2.0")

        implementation("it.unimi.dsi:fastutil:8.3.1")

        implementation("org.koin:koin-core:2.1.5")

        implementation("org.koin:koin-gradle-plugin:2.1.5")

        implementation("com.google.guava:guava:29.0-jre")

        implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")

        implementation("com.github.ben-manes.caffeine:caffeine:2.8.4")

        implementation("com.google.code.gson:gson:2.8.6")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.2")

        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")

    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
    }

    publishing {
        repositories {
            maven {
                setUrl("http://localhost:8081/repository/snapshots/")
                credentials {
                    username = "admin"
                    password = "deadmau5"
                }
            }
        }
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
            }
        }
    }

}