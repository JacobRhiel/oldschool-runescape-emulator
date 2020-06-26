group = "rs.emulator"
version = "1.0-SNAPSHOT"

dependencies {

    implementation(project(":rse-api:entity"))

    implementation(project(":rse-utilities"))

    implementation(project(":rse-network"))

    implementation(project(":rse-network-packet"))

    implementation(project(":rse-encryption"))

    implementation(project(":rse-entity:player"))

    implementation(project(":rse-world"))

}