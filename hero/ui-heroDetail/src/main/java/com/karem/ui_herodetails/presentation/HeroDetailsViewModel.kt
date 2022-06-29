package com.karem.ui_herodetails.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karem.core.DataState
import com.karem.core.Logger
import com.karem.core.UIComponent
import com.karem.hero_interactors.GetHeroFromCache
import com.karem.ui_herodetails.ui.HeroDetailEvents
import com.karem.ui_herodetails.ui.HeroDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroDetailsViewModel
@Inject
constructor(
    private val getHeroFromCache: GetHeroFromCache,
    savedStateHandle: SavedStateHandle,
    private val logger: Logger
): ViewModel(){

    val state: MutableState<HeroDetailState> = mutableStateOf(HeroDetailState())

    init {
        savedStateHandle.get<Int>("heroId")?.let { heroId ->
            onTriggerEvent(HeroDetailEvents.GetHeroFromCache(heroId))
        }
    }

    fun onTriggerEvent(event: HeroDetailEvents) {
        when (event) {
            is HeroDetailEvents.GetHeroFromCache -> {
                getHeroFromCache(event.id)
            }
            is HeroDetailEvents.onRemoveHeadFromQueue -> {
                removeHeadFromQueue()
            }
        }
    }

    private fun getHeroFromCache(id: Int){
        getHeroFromCache.execute(id).onEach { dataState ->
            when(dataState){
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(hero = dataState.data)
                }
                is DataState.Response -> {
                    when(dataState.uiComponent){
                        is UIComponent.Dialog ->{
                            appendMessageToQueue(dataState.uiComponent)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendMessageToQueue(uiComponent: UIComponent){
        val queue = state.value.error
        queue.add(uiComponent)
        state.value = state.value.copy(error = queue)
    }

    private fun removeHeadFromQueue() {
        try {
            val queue = state.value.error
            queue.remove()
            state.value = state.value.copy(error = queue)
        }catch (e: Exception){
            logger.log("Nothing to remove from dialog")
        }
    }
}
