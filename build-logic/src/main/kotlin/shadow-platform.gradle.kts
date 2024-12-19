plugins {
    id("platform-conventions")
    id("com.gradleup.shadow")
}

tasks {
    jar {
        archiveClassifier = "unshaded"
        dependsOn(check)
    }

    shadowJar {
        dependsOn(check)
        archiveClassifier.set(null as String?)
        relocateDependency("io.leangen.geantyref")
        relocateDependency("org.spongepowered.configurate")
    }
}

extensions.configure<PlatformExtension> {
    productionJar = tasks.shadowJar.flatMap { it.archiveFile }
}
