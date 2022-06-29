package com.karem.ui_herodetails.ui

sealed class HeroDetailEvents {

    data class GetHeroFromCache(
        val id: Int,
    ) : HeroDetailEvents()

    object onRemoveHeadFromQueue:HeroDetailEvents()

}