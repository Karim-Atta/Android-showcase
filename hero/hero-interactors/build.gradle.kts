
apply {
    from("$rootDir/library-build.gradle")
}

dependencies{
    "implementation"(project(Modules.heroDataSource))
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.heroDomain))
    "implementation"(Kotlinx.coroutinesCore)
    "implementation"(project(Modules.heroDataSourceTest))
    "implementation"(Ktor.ktorClientMock)
    "implementation"(Ktor.serialization)
    "implementation"(Ktor.contentNegotiation)
    "implementation"(Junit.junit4)
}