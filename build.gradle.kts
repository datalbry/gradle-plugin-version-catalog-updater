plugins {
    id("datalbry.publish-maven-central")
    id("datalbry.version")
    idea
}

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

group = "io.datalbry.connector"

val values = tasks.create<Copy>("prepareDocs") {
    from("templates/docs")
    into("docs")
    expand(project.properties)
}
