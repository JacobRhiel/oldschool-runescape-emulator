import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {

    id("com.github.johnrengelman.shadow") version "6.0.0"

}

tasks.withType<ShadowJar> {
    archiveBaseName.set("rs-emulator-api")
    archiveVersion.set("1.0")
    dependencies {
        include(project(":rse-api:collections"))
        include(project(":rse-api:core"))
        include(project(":rse-api:definitions"))
        include(project(":rse-api:entity"))
        include(project(":rse-api:reactive"))
        include(project(":rse-api:region"))
        include(project(":rse-api:skills"))
        include(project(":rse-api:widgets"))
    }
}

dependencies {
    api(project(":rse-api:collections"))
    api(project(":rse-api:core"))
    api(project(":rse-api:definitions"))
    api(project(":rse-api:entity"))
    api(project(":rse-api:reactive"))
    api(project(":rse-api:region"))
    api(project(":rse-api:skills"))
    api(project(":rse-api:widgets"))
}