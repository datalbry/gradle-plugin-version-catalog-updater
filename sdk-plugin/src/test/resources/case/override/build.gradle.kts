import io.datalbry.plugin.catalog.updater.CatalogUpdaterPluginExtension

plugins{
    id("io.datalbry.connector.sdk")
}

group = "io.datalbry.test"

connector {
    name = "test"
    version = "1.9"
    oidc {
        baseUrl = "google.com"
    }
}

tasks.register<DefaultTask>("assertExtensionIsBeingUpdated") {
    doLast {
        val extension = project.extensions.getByType(CatalogUpdaterPluginExtension::class.java)
        assertEquals(extension.name!!, "test")
        assertEquals(extension.group!!, "io.datalbry.test")
        assertEquals(extension.version!!, "1.9")
        assertEquals(extension.oidc.baseUrl, "google.com")
    }
    outputs.upToDateWhen { false }
}

fun assertEquals(awaited: String, current: String) {
    if (awaited != current) {
        throw GradleException("Failed")
    }
}
