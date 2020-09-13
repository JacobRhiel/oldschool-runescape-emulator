version = "1.0-SNAPSHOT"

dependencies {

    api(project(":rse-entity:actor"))
    api(project(":rse-entity:update"))
    api(project(":rse-api:core"))
    api(project(":rse-entity:widget"))
    api(project(":rse-entity:npc"))
    api(project(":rse-entity:details"))

    api(project(":rse-packet:message"))

    implementation(project(":rse-map"))

}

tasks.withType<Test> {
    useJUnitPlatform()
}