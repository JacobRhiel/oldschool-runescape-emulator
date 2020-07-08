group = "rs.emulator"
version = "1.0-SNAPSHOT"

dependencies {

    api(project(":rse-network"))

    api(project(":rse-entity:player"))

    api(project(":rse-packet:packet-api"))
    api(project(":rse-api:core"))

    implementation(project(":rse-encryption"))

    implementation(project(":rse-entity:widget"))

}