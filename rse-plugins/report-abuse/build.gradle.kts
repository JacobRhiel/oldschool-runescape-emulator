val jar: Jar by tasks

dependencies {
    api(project(":rse-api:core"))
}

jar.apply {
    manifest {
        attributes["Plugin-Class"] = "rs.emulator.plugin.ReportAbusePlugin"
        attributes["Plugin-Id"] = "REPORT-ABUSE"
        attributes["Plugin-Provider"] = "Javatar"
        attributes["Plugin-Version"] = "1"
    }
    from(configurations.named("provideDependency").get().map { if (it.isDirectory) it else zipTree(it) })
}