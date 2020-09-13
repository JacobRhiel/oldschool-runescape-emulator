version = "unspecified"

dependencies {

    api(project(":rse-buffer"))

    api(project(":rse-utilities"))

    api(project(":rse-api:core"))

    implementation("org.bouncycastle:bcprov-jdk16:1.46")

    implementation("org.apache.commons:commons-compress:1.20")

    api(project(":rse-encryption"))

}

tasks.withType<Test> {

    useJUnitPlatform()

}