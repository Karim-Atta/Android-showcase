package com.karem.hero_datasource_test.network

import com.karem.hero_datasource.network.HeroDto
import com.karem.hero_datasource.network.toHero
import com.karem.hero_domain.Hero
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


private val json = Json {
    ignoreUnknownKeys = true
}

fun serializeHeroData(jsonData: String): List<Hero>{
    val heros: List<Hero> = json.decodeFromString<List<HeroDto>>(jsonData).map { it.toHero() }
    return heros
}