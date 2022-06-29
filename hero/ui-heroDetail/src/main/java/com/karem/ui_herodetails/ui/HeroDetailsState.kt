package com.karem.ui_herodetails.ui

import com.karem.core.ProgressBarState
import com.karem.core.Queue
import com.karem.core.UIComponent
import com.karem.hero_domain.Hero

data class HeroDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val hero: Hero? = null,
    val error: Queue<UIComponent> = Queue(mutableListOf()),
)