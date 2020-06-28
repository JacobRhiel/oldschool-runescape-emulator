group = "rs.emulator"
version = "1.0-SNAPSHOT"

dependencies {

    api(project(":rse-network"))

    api(project(":rse-entity:player"))

    api(project(":rse-api:packet"))
    api(project(":rse-api:plugins"))

    implementation(project(":rse-encryption"))

    implementation(project(":rse-entity:widget"))

}