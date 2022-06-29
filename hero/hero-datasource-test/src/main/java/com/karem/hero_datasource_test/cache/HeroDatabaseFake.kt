package com.karem.hero_datasource_test.cache

import com.karem.hero_domain.Hero

class HeroDatabaseFake {
    val heros: MutableList<Hero> = mutableListOf()
}