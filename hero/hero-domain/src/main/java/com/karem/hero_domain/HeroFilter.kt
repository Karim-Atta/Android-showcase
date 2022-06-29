package com.karem.hero_domain

import com.karem.core.FilterOrder

sealed class HeroFilter(val uiValue: String) {

    data class Hero(
        val order: FilterOrder = FilterOrder.Descending
    ): HeroFilter("Hero")

    data class ProWins(
        val order: FilterOrder = FilterOrder.Descending
    ): HeroFilter("Pro win-rate")
}