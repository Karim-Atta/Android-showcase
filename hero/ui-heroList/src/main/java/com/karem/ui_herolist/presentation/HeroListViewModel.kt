package com.karem.ui_herolist.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karem.core.*
import com.karem.hero_domain.HeroFilter
import com.karem.hero_interactors.FilterHeros
import com.karem.hero_interactors.GetHeros
import com.karem.ui_herolist.ui.HeroListEvents
import com.karem.ui_herolist.ui.HeroListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroListViewModel
@Inject
constructor(
    private val getHeros: GetHeros,
    private val filterHeros: FilterHeros,
    @Named("heroListLogger") private val logger: Logger,
):ViewModel(){

    val state: MutableState<HeroListState> = mutableStateOf(HeroListState())

    init {
        onTriggerEvents(HeroListEvents.GetHeros)
    }

    fun onTriggerEvents(event: HeroListEvents){
        when(event){
            is HeroListEvents.GetHeros -> {
                getHeros()
            }
            is HeroListEvents.FilterHero -> {
                filterHero()
            }
            is HeroListEvents.UpdateHeroName -> {
                updateHeroName(event.heroName)
            }
            is HeroListEvents.UpdateHeroFilter ->{
                updateHeroFilter(event.heroFilter)
            }
            is HeroListEvents.UpdateFilterDialogState ->{
                state.value = state.value.copy(filterDialogState = event.uiComponentState)
            }
            is HeroListEvents.UpdateAttributeFilter ->{
                state.value = state.value.copy(primaryAttribute = event.attribute)
                filterHero()
            }
            is HeroListEvents.OnRemoveHeadFromQueue -> {
                removeHeadFromQueue()
            }
        }
    }

    private fun removeHeadFromQueue() {
        try {
            val queue = state.value.error
            queue.remove()
            state.value = state.value.copy(error = Queue(mutableListOf()))
            state.value = state.value.copy(error = queue)
        }catch (e: Exception){
            logger.log("Nothing to remove from dialog")
        }
    }

    private fun updateHeroFilter(heroFilter: HeroFilter) {
        state.value = state.value.copy(heroFilter = heroFilter)
        filterHero()
    }

    private fun filterHero() {
        val filteredList = filterHeros.execute(
            current = state.value.heros,
            heroName = state.value.heroName,
            heroFilter = state.value.heroFilter,
            attributeFilter = state.value.primaryAttribute
        )
        state.value = state.value.copy(filteredHeros = filteredList)
    }

    private fun updateHeroName(heroName: String) {
        state.value = state.value.copy(heroName = heroName)
    }

    private fun getHeros() {
        getHeros.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            appendMessageToQueue(dataState.uiComponent)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Data -> {
                    state.value = state.value.copy(heros = dataState.data ?: listOf(), progressBarState = ProgressBarState.Idle)
                    filterHero()
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(
                        progressBarState = dataState.progressBarState
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendMessageToQueue(uiComponent: UIComponent){
        val queue = state.value.error
        queue.add(uiComponent)
        state.value = state.value.copy(error = queue)
    }
}