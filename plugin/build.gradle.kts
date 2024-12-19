plugins {
    id("shadow-platform")
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.2.0" // paper plugin
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

dependencies {
    compileOnly(libs.paper)
    implementation(project(":${rootProject.name}-api"))
    implementation(libs.configurate.hocon)
}

tasks {
    runServer {
        minecraftVersion(libs.versions.paper.get().split("-R0.1-SNAPSHOT")[0])

        systemProperty("terminal.jline", false)
        systemProperty("terminal.ansi", true)
    }
}

paperPluginYaml {
    main = "${rootProject.group}.${rootProject.name}.Soulbinding"
    bootstrapper = "${rootProject.group}.${rootProject.name}.SoulbindingBootstrap"
    name = rootProject.name
    authors.add("kokiriglade")
    apiVersion = libs.versions.paper.get().split("-R0.1-SNAPSHOT")[0]
}
