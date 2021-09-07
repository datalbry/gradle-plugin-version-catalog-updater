package io.datalbry.plugin.catalog.updater.model

data class Library(
    val key: String,
    val module: String,
    val versionRef: String? = null,
    val version: String? = null
)
