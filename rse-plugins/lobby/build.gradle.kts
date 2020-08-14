val jar: Jar by tasks

dependencies {
    api(project(":rse-api:core"))
    api(project(":rse-api:definitions"))
}

jar.apply {
    manifest {
        attributes["Plugin-Class"] = "rs.emulator.plugin.LobbyPlugin"
        attributes["Plugin-Id"] = "LOBBY-PLUGIN"
        attributes["Plugin-Provider"] = "Javatar"
        attributes["Plugin-Version"] = "1"
    }
    from(configurations.named("provideDependency").get().map { if (it.isDirectory) it else zipTree(it) })
}