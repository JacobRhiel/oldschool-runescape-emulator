version = "1.0-SNAPSHOT"

dependencies {

    api(project(":rse-entity:actor"))
    api(project(":rse-entity:update"))
    api(project(":rse-api:entity"))

    api(project(":rse-packet:message"))

}

tasks.withType<Test> {
    useJUnitPlatform()
}