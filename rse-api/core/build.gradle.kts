dependencies {
    api(group = "org.pf4j", name = "pf4j", version = "3.3.1")
    api("org.hibernate.orm:hibernate-core:6.0.0.Alpha5")
    api("org.hibernate.orm:hibernate-hikaricp:6.0.0.Alpha5")
    api("org.postgresql:postgresql:42.2.14")
    implementation(project(":rse-utilities"))
    api("com.xenomachina:kotlin-argparser:2.0.7")
}

tasks.withType<Test> {
    useJUnitPlatform()
}