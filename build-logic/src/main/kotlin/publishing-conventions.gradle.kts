plugins {
    id("base-conventions")
    `maven-publish`
}

tasks {
    javadoc {
        dependsOn(check)
        options {
            (this as StandardJavadocDocletOptions).apply {
                encoding = Charsets.UTF_8.name()
                addStringOption("tag", "implNote:a:Implementation Note")
                links(
                    "https://jd.papermc.io/paper/${libs.versions.paper.get().split("-R0.1-SNAPSHOT")[0]}/",
                    "https://jd.advntr.dev/api/4.17.0/",
                    "https://jd.advntr.dev/nbt/4.17.0/",
                    "https://jd.advntr.dev/text-minimessage/4.17.0/",
                    "https://jd.advntr.dev/key/4.17.0/",
                )
            }
        }
    }
}

configure<PublishingExtension> {
    publications {
        register<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}
