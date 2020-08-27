dependencies {
    api(group = "org.pf4j", name = "pf4j", version = "3.3.1")
    api(project(":rse-api:entity"))
    api(project(":rse-api:widgets"))
    api(project(":rse-api:definitions"))
    api(project(":rse-api:database"))
    api("com.xenomachina:kotlin-argparser:2.0.7")
}

tasks.withType<Test> {
    useJUnitPlatform()
}