
apply {
    from("$rootDir/library-build.gradle")
}

plugins{
    kotlin("plugin.serialization") version "1.6.21"
}

dependencies{
    "implementation"(Kotlin.kotlin_reflect)
    "implementation"(project(Modules.heroDataSource))
    "implementation"(project(Modules.heroDomain))

    "implementation"(Ktor.ktorClientMock)
    "implementation"(Ktor.serialization)
    "implementation"(Ktor.contentNegotiation)

}
