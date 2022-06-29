package com.karem.hero_interactors

import com.karem.core.DataState
import com.karem.core.ProgressBarState
import com.karem.core.UIComponent
import com.karem.hero_datasource.cache.HeroCache
import com.karem.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeroFromCache(
    private val cache: HeroCache
) {

    fun execute(
        id: Int
    ): Flow<DataState<Hero>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            val cachedHero =
                cache.getHero(id) ?: throw Exception("that Hero doesn't exist in the cache.")
            emit(DataState.Data(cachedHero))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown Error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}