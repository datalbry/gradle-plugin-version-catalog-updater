package io.datalbry.plugin.catalog.updater.extensions

import io.datalbry.plugin.catalog.updater.model.Version

fun Set<Version>.containsKey(key: String) =
    any { it.key == key }
