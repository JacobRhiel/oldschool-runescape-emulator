dependencies {
    api(project(":rse-utilities"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}