dependencies {
    api(project(":rse-utilities"))
    api(project(":rse-api:core"))
    testApi(project(":rse-entity:player"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}