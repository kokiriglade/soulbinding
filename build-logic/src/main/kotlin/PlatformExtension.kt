import org.gradle.api.file.RegularFileProperty

abstract class PlatformExtension {
    abstract val productionJar: RegularFileProperty
}
