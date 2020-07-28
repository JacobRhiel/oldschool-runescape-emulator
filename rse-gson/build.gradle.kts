dependencies {
    api(project(":rse-api:entity"))
    api(project(":rse-utilities"))

    testApi(project(":rse-entity:player"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}