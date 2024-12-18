import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.the
import xyz.jpenilla.gremlin.gradle.ShadowGremlin

// https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
val Project.libs: LibrariesForLibs
    get() = the()

/**
 * Relocate a package into the `com.example.template.libs` namespace.
 */
fun Task.relocateDependency(pkg: String) {
    ShadowGremlin.relocate(this, pkg, "com.example.template.libs.$pkg")
}
