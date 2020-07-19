group = "rs.emulator"
version = "1.0-SNAPSHOT"

dependencies {

    implementation(project(":rse-entity:player"))

    implementation(project(":rse-entity:npc"))

    implementation(project(":rse-packet:message"))

    implementation(project(":rse-packet:decoders"))

    implementation(project(":rse-packet:encoders"))

    implementation(project(":rse-network-packet"))

    implementation(project(":rse-network-js5"))

}