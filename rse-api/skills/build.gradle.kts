tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    api(project(":rse-api:reactive"))
}