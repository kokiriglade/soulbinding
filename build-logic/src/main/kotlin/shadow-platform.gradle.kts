plugins {
    id("platform-conventions")
    id("com.gradleup.shadow")
    id("xyz.jpenilla.gremlin-gradle")
}

tasks {
    jar {
        archiveClassifier = "unshaded"
        dependsOn(check)
    }

    shadowJar {
        archiveClassifier.set(null as String?)
        relocateDependency("xyz.jpenilla.gremlin")
    }

    writeDependencies {
    }
}

extensions.configure<PlatformExtension> {
    productionJar = tasks.shadowJar.flatMap { it.archiveFile }
}
