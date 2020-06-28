version = "1.0-SNAPSHOT"

dependencies {

    implementation(project(":rse-database"))

    implementation(project(":rse-api:collections"))

    api(project(":rse-entity:player"))

    implementation(project(":rse-packet:message"))

    implementation(project(":rse-packet:decoders"))

    implementation(project(":rse-packet:encoders"))

    implementation(project(":rse-network-packet"))

}