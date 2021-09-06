import org.jetbrains.kotlin.util.removeSuffixIfPresent
import org.jetbrains.kotlin.util.suffixIfNot

val projectVersion: String by project
version = findVersion(projectVersion)

fun findVersion(baseVersion: String): String {
    return if (project.hasProperty("snapshot")) {
        return baseVersion.suffixIfNot("-SNAPSHOT")
    } else baseVersion
}

tasks.register<WriteProperties>("writeVersion") {
    this.outputFile = project.rootProject.file("gradle.properties")
    this.property("projectVersion", version)
}

tasks.register<WriteProperties>("incrementVersion") {
    val currentVersion = projectVersion.removeSuffixIfPresent("-SNAPSHOT").substringAfterLast(".")
    val newVersion = "${projectVersion.substringBeforeLast(".")}.${currentVersion + 1}"
    this.outputFile = project.rootProject.file("gradle.properties")
    this.property("projectVersion", newVersion)
}
