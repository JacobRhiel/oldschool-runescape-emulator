plugins {
    id("org.jetbrains.kotlin.kapt")
}


subprojects {
    apply(plugin = "kotlin-kapt")

    configurations.create("provideDependency")

    dependencies {
        implementation("org.pf4j", "pf4j", "3.3.1")
        kapt("org.pf4j:pf4j:3.3.1")
    }
}
