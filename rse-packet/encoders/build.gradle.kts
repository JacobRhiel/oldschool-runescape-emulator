dependencies {

    api(project(":rse-packet:packet-api"))

    api(project(":rse-packet:message"))

    api(project(":rse-api:region"))

    api(project(":rse-api:entity"))

    implementation(project(":rse-encryption"))

    implementation(project(":rse-entity:player"))

    implementation(project(":rse-entity:npc"))

}