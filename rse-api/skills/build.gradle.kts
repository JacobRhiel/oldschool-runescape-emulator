tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    api(project(":rse-api:reactive"))
    api(project(":rse-utilities"))
}