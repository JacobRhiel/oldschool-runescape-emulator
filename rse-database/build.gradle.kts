version = "1.0-SNAPSHOT"

dependencies {

    api("org.hibernate.orm:hibernate-core:6.0.0.Alpha5")

    api("org.hibernate.orm:hibernate-hikaricp:6.0.0.Alpha5")

    api("org.postgresql:postgresql:42.2.14")

    implementation(project(":rse-utilities"))

}