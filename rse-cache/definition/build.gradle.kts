group = "rs.emulator"
version = "1.0-SNAPSHOT"

dependencies {

    api(project(":rse-cache"))

    api(project(":rse-api:core"))

}

tasks.withType<Test> {

    useJUnitPlatform()

}