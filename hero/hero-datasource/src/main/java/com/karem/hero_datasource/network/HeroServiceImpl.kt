package com.karem.hero_datasource.network

import com.karem.hero_domain.Hero
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*


class HeroServiceImpl(
    private val httpClient: HttpClient
    ): HeroService {
    override suspend fun getHeroStats(): List<Hero> {
       return httpClient.get{
            url(EndPoints.HERO_STATS)
        }.body<List<HeroDto>>().map {  it.toHero() }
    }
}