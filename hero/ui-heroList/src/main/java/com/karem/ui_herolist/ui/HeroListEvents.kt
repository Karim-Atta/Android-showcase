package com.karem.ui_herolist.ui

import com.karem.core.UIComponentState
import com.karem.hero_domain.HeroAttribute
import com.karem.hero_domain.HeroFilter

sealed class HeroListEvents {
    object GetHeros: HeroListEvents()
    object FilterHero: HeroListEvents()
    data class UpdateHeroName(
        val heroName: String
    ):HeroListEvents()

    data class UpdateHeroFilter(
        val heroFilter: HeroFilter
    ): HeroListEvents()

    data class UpdateFilterDialogState(
        val uiComponentState: UIComponentState
    ): HeroListEvents()

    data class UpdateAttributeFilter(
        val attribute: HeroAttribute
    ): HeroListEvents()

    object OnRemoveHeadFromQueue:HeroListEvents()
}