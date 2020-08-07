dependencies {

    api(project(":rse-api:collections"))
    api(project(":rse-api:definitions"))
    api(project(":rse-api:skills"))
    api(project(":rse-api:region"))
    api(project(":rse-api:persistent-details"))
    api(project(":rse-utilities"))

    testApi(project(":rse-cache"))
    testApi(project(":rse-cache:definition"))

}

tasks.withType<Test> {
    useJUnitPlatform()
}