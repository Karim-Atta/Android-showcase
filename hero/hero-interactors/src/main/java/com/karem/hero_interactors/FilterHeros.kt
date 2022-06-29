package com.karem.hero_interactors

import com.karem.core.FilterOrder
import com.karem.hero_domain.Hero
import com.karem.hero_domain.HeroAttribute
import com.karem.hero_domain.HeroFilter
import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.math.round

class FilterHeros {

    fun execute(
        current: List<Hero>,
        heroName: String,
        heroFilter: HeroFilter,
        attributeFilter: HeroAttribute
    ): List<Hero> {

        var filteredList: MutableList<Hero> = current.filter { hero ->
            hero.localizedName.lowercase().contains(heroName.lowercase())
        }.toMutableList()

        when (heroFilter) {
            is HeroFilter.Hero -> {
                when (heroFilter.order) {
                    is FilterOrder.Descending -> {
                        filteredList.sortByDescending { it.localizedName }
                    }
                    is FilterOrder.Ascending -> {
                        filteredList.sortBy { it.localizedName }
                    }
                }
            }
            is HeroFilter.ProWins -> {
                when (heroFilter.order) {
                    is FilterOrder.Descending -> {
                        filteredList.sortByDescending { hero ->
                            getWinRate(hero.proWins.toDouble(), hero.proPick.toDouble())
                        }
                    }
                    is FilterOrder.Ascending -> {
                        filteredList.sortBy { hero ->
                            getWinRate(hero.proWins.toDouble(), hero.proPick.toDouble())
                        }
                    }
                }
            }
        }
        when (attributeFilter) {
            is HeroAttribute.Strength -> {
                filteredList = filteredList.filter { it.primaryAttribute is HeroAttribute.Strength }
                    .toMutableList()
            }
            is HeroAttribute.Agility -> {
                filteredList = filteredList.filter { it.primaryAttribute is HeroAttribute.Agility }
                    .toMutableList()
            }
            is HeroAttribute.Intelligence -> {
                filteredList =
                    filteredList.filter { it.primaryAttribute is HeroAttribute.Intelligence }
                        .toMutableList()
            }
            is HeroAttribute.Unknown -> {
                //don not filter
            }
        }
        return filteredList
    }

    private fun getWinRate(proWins: Double, proPick: Double): Int{
        return if (proPick <= 0){
            0
        }else{
            val winRate =
                round(proWins / proPick * 100).toInt()
            winRate
        }
    }
}