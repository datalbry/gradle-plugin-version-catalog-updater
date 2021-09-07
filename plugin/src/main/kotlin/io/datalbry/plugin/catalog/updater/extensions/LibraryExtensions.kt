package io.datalbry.plugin.catalog.updater.extensions

import com.github.benmanes.gradle.versions.updates.Coordinate
import io.datalbry.plugin.catalog.updater.model.Catalog
import io.datalbry.plugin.catalog.updater.model.Library
import io.datalbry.plugin.catalog.updater.model.Version

fun Library.enrichVersion(catalog: Catalog) =
    if (hasVersion()) this else copy(version = catalog.getArtifactId(versionRef!!))

fun Library.enrichLatestVersion(
    latestVersions: List<Coordinate>
): Library {
    val group = getGroup()
    val artifact = getArtifactId()
    val version = latestVersions.firstOrNull { it.artifactId == artifact && it.groupId == group }?.version
    return copy(version = version)
}

fun Library.toVersion() =
    Version(versionRef!!, version!!)

fun Library.hasVersion() =
    version != null

fun Library.hasVersionOrVersionRef() =
    versionRef != null || hasVersion()

fun Library.hasVersionRef() =
    versionRef != null

fun Library.getGroup() =
    module.split(":")[0]

fun Library.getArtifactId() =
    module.split(":")[1]

fun Collection<Library>.containsModule(module: String) =
    any { it.module == module }

fun Library.removeExplicitVersionIfNotPresentBefore(
    catalog: Catalog,
): Library {
    val old = catalog.libraries.first { this.key == it.key && this.module == it.module }
    return if (!old.hasVersion()) {
        old.copy(version = null)
    } else this
}
