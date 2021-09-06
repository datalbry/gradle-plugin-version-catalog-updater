package io.datalbry.plugin.catalog.updater.util

/**
 * Checks if a given [version] is stable or not
 *
 * @param version to check
 *
 * @return true if the version is stable version, else false
 */
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
