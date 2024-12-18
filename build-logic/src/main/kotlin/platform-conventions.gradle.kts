plugins {
    id("base-conventions")
}

val platformExtension = extensions.create<PlatformExtension>("platform")

tasks {
    val copyJar = register<FileCopyTask>("copyJar") {
        fileToCopy = platformExtension.productionJar
        destination = rootProject.layout.buildDirectory.dir("libs").flatMap {
            it.file(fileToCopy.map { file -> file.asFile.name })
        }
    }
    build {
        dependsOn(copyJar)
    }
}
