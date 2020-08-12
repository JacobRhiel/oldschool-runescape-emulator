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

    tasks.create<Copy>("Copy") {
        from("$buildDir/libs/*.jar")
        into(System.getProperty("user.home") + "/rs-emulator/plugins/")
    }

    tasks.register("copy-plugin", Copy::class.java) {
        from("$buildDir/libs/") {
            include("*.jar")
        }
        into("${System.getProperty("user.home")}/rs-emulator/plugins")
    }
    tasks.named("jar") {
        finalizedBy(tasks.named("copy-plugin"))
    }
}
