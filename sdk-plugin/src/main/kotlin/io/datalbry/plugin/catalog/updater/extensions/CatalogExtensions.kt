package io.datalbry.plugin.catalog.updater.extensions

import io.datalbry.plugin.catalog.updater.model.Catalog

fun Catalog.getArtifactId(reference: String) =
    versions.find { v -> v.key == reference }!!.version
