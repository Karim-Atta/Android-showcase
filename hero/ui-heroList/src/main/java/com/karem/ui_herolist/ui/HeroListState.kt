package com.karem.ui_herolist.ui

import com.karem.core.ProgressBarState
import com.karem.core.Queue
import com.karem.core.UIComponent
import com.karem.core.UIComponentState
import com.karem.hero_domain.Hero
import com.karem.hero_domain.HeroAttribute
import com.karem.hero_domain.HeroFilter

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heros: List<Hero> = listOf(),
    val filteredHeros : List<Hero> = listOf(),
    val heroName: String= "",
    val heroFilter: HeroFilter = HeroFilter.Hero(),
    val filterDialogState: UIComponentState = UIComponentState.Hide,
    val primaryAttribute: HeroAttribute = HeroAttribute.Unknown,
    val error: Queue<UIComponent> = Queue(mutableListOf()),

)
