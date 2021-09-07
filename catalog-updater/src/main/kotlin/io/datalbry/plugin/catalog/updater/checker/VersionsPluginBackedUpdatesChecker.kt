package io.datalbry.plugin.catalog.updater.checker

import com.github.benmanes.gradle.versions.updates.Coordinate
import com.github.benmanes.gradle.versions.updates.DependencyUpdates
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesReporter
import io.datalbry.plugin.catalog.updater.extensions.containsKey
import io.datalbry.plugin.catalog.updater.extensions.containsModule
import io.datalbry.plugin.catalog.updater.extensions.enrichLatestVersion
import io.datalbry.plugin.catalog.updater.extensions.enrichVersion
import io.datalbry.plugin.catalog.updater.extensions.hasVersion
import io.datalbry.plugin.catalog.updater.extensions.hasVersionOrVersionRef
import io.datalbry.plugin.catalog.updater.extensions.hasVersionRef
import io.datalbry.plugin.catalog.updater.extensions.removeExplicitVersionIfNotPresentBefore
import io.datalbry.plugin.catalog.updater.extensions.toVersion
import io.datalbry.plugin.catalog.updater.model.Catalog
import io.datalbry.plugin.catalog.updater.model.Library
import io.datalbry.plugin.catalog.updater.model.Version
import io.datalbry.plugin.catalog.updater.util.isNonStable
import org.gradle.api.Project

/**
 * [VersionsPluginBackedUpdatesChecker] uses the famous Ben Manes version plugin to search for updates for
 * the catalog
 *
 * @param project, which is calling the task
 *
 * @author timo gruen - 2021-09-06
 */
class VersionsPluginBackedUpdatesChecker(
    private val project: Project
): UpdatesChecker {

    override fun updateToLatest(catalog: Catalog): Catalog {
        project.configurations.create("catalog")
        catalog.libraries
            .filter { it.hasVersionOrVersionRef() }
            .map { it.enrichVersion(catalog) }
            .forEach { project.dependencies.add("catalog", "${it.module}:${it.version}")}

        val updates = checkUpdates()
        val upgradeVersions = updates.upgradeVersions.map { it.value }
        val latestVersions = updates.latestVersions.map { it.value }
            .filter { c -> upgradeVersions.any { it.groupId == c.groupId && it.artifactId == c.artifactId } }

        val updatedVersions = calculateUpdatedVersions(catalog, latestVersions)
        val updatedLibraries = calculateUpdatedLibraries(catalog, latestVersions)
            .map { it.removeExplicitVersionIfNotPresentBefore(catalog) }
            .toSet()
        val unUpdatedVersions = catalog.versions.filter { !updatedVersions.containsKey(it.key) }
        val unUpdatedLibraries = catalog.libraries.filter { !updatedLibraries.containsModule(it.module) }

        val versions = updatedVersions + unUpdatedVersions
        val libraries = updatedLibraries + unUpdatedLibraries

        return catalog.copy(versions = versions, libraries = libraries)
    }



    private fun calculateUpdatedLibraries(catalog: Catalog, latestVersions: List<Coordinate>): Set<Library> {
        return catalog.libraries
            .asSequence()
            .filter { it.hasVersionRef() }
            .map { it.enrichLatestVersion(latestVersions) }
            .filter { it.hasVersion() }
            .toSet()
    }

    private fun calculateUpdatedVersions(catalog: Catalog, latestVersions: List<Coordinate>): Set<Version> {
        return catalog.libraries
            .asSequence()
            .filter { it.hasVersionRef() }
            .map { it.enrichLatestVersion(latestVersions) }
            .filter { it.hasVersion() }
            .map { it.toVersion() }
            .distinctBy { it.key }
            .toSet()
    }


    private fun checkUpdates(): DependencyUpdatesReporter {
        val updatesChecker = DependencyUpdates(project)
        updatesChecker.revision = "release"
        updatesChecker.checkForGradleUpdate = false
        updatesChecker.checkConstraints = true
        updatesChecker.setResolutionStrategy { strategy ->
            strategy.componentSelection { selectionRules ->
                selectionRules.all { selectionRule ->
                    if (isNonStable(selectionRule.candidate.version) && !isNonStable(selectionRule.currentVersion)) {
                        selectionRule.reject("Release candidate")
                    }
                }
            }
        }
        return updatesChecker.run()
    }

}
