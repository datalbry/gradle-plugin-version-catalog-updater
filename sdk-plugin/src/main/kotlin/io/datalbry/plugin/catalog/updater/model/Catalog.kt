package io.datalbry.plugin.catalog.updater.model

data class Catalog(
    val versions: Set<Version>,
    val libraries: Set<Library>,
    val bundles: Set<Bundle>
)
