plugins {
    idea
    id("net.kyori.indra")
    id("net.kyori.indra.checkstyle")
    id("net.kyori.indra.licenser.spotless")
}

version = rootProject.version

indra {
    gpl3OrLaterLicense()

    javaVersions {
        target(21)
    }
}

indraSpotlessLicenser {
    licenseHeaderFile(rootProject.file("license_header.txt"))
}

dependencies {
    checkstyle(libs.stylecheck)
}

tasks {
    jar {
        dependsOn(check)
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}
