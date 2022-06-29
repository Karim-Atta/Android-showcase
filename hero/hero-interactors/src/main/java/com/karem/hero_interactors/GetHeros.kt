package com.karem.hero_interactors

import com.karem.core.DataState
import com.karem.core.ProgressBarState
import com.karem.core.UIComponent
import com.karem.hero_datasource.cache.HeroCache
import com.karem.hero_datasource.network.HeroService
import com.karem.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeros(
    private val service: HeroService,
    private val cache: HeroCache
) {
    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val heros: List<Hero> = try {
                service.getHeroStats()
            } catch (e: Exception) {
                emit(
                    DataState.Response(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown Error"
                        )
                    )
                )
                e.printStackTrace()
                listOf()
            }
            cache.insert(heros)
            val cachedHeros = cache.selectAll()
            emit(DataState.Data(cachedHeros))

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
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}