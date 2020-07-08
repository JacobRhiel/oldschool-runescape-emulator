val jar: Jar by tasks

dependencies {
    api(project(":rse-api:core"))
    api("com.xenomachina:kotlin-argparser:2.0.7")
    //provideDependency("com.xenomachina:kotlin-argparser:2.0.7")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jar.apply {
    manifest {
        attributes["Plugin-Class"] = "rs.emulator.plugin.commands.CommandPlugin"
        attributes["Plugin-Id"] = "COMMAND_PLUGIN"
        attributes["Plugin-Provider"] = "Javatar"
        attributes["Plugin-Version"] = "1"
    }
    from(configurations.named("provideDependency").get().map { if(it.isDirectory) it else zipTree(it) })
}

