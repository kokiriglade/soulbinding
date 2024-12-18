plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.shadow)
    implementation(libs.gremlin.gradle)
    implementation(libs.indra.common)
    implementation(libs.indra.licenseHeader)
    implementation(libs.indra.git)

    // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
