version = "unspecified"

dependencies {

    api(project(":rse-buffer"))

    api(project(":rse-utilities"))

    api(project(":rse-api:definitions"))

    implementation("org.bouncycastle:bcprov-jdk16:1.46")

    implementation("org.apache.commons:commons-compress:1.20")

    implementation(project(":rse-encryption"))

}