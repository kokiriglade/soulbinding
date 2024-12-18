//import io.papermc.paperweight.userdev.ReobfArtifactConfiguration.Companion.MOJANG_PRODUCTION // paperweight

plugins {
    id("shadow-platform")
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.2.0"
//    id("xyz.jpenilla.resource-factory-paper-convention") version "1.2.0" // paper plugin
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

//paperweight.reobfArtifactConfiguration = MOJANG_PRODUCTION // paperweight

dependencies {
//    paperweight.paperDevBundle(libs.versions.paper.get()) // paperweight
    compileOnly(libs.paper)
    implementation(project(":${rootProject.name}-api"))
}

tasks {
    runServer {
        minecraftVersion(libs.versions.paper.get().split("-R0.1-SNAPSHOT")[0])

        systemProperty("terminal.jline", false)
        systemProperty("terminal.ansi", true)
    }
}

bukkitPluginYaml {
    main = "${rootProject.group}.${rootProject.name}.TemplatePlugin"
    name = rootProject.name
    authors.add("somebody")
    apiVersion = libs.versions.paper.get().split("-R0.1-SNAPSHOT")[0]
}
