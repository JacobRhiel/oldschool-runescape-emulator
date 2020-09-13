val jar: Jar by tasks

dependencies {
    api(project(":rse-api:core"))
    api(project(":rse-utilities"))
}

jar.apply {
    manifest {
        attributes["Plugin-Class"] = "rs.emulator.entity.social.SocialPlugin"
        attributes["Plugin-Id"] = "social-network"
        attributes["Plugin-Provider"] = "Chk"
        attributes["Plugin-Version"] = "1.0"
    }
    from(configurations.named("provideDependency").get().map { if (it.isDirectory) it else zipTree(it) })
}