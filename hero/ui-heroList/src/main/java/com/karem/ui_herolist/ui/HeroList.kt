
package com.karem.ui_herolist.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.EventListener
import coil.ImageLoader
import com.karem.components.DefaultScreenUI
import com.karem.core.ProgressBarState
import com.karem.core.UIComponentState
import com.karem.ui_herolist.components.HeroListFilter
import com.karem.ui_herolist.components.HeroListItem
import com.karem.ui_herolist.components.HeroListToolbar

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun HeroList(
    state: HeroListState,
    events: (HeroListEvents) -> Unit,
    imageLoader: ImageLoader,
    navigateToDetailsScreen: (Int) -> Unit,
){
    DefaultScreenUI(
        queue = state.error,
        progressBarState = state.progressBarState,
        onRemoveHeadFromDialog = {
            events(HeroListEvents.OnRemoveHeadFromQueue)
        }
    ) {
        Column {
            HeroListToolbar(
                heroName = state.heroName,
                onHeroNameChanged = { heroName ->
                    events(HeroListEvents.UpdateHeroName(heroName))
                },
                onExecuteSearch = {
                    events(HeroListEvents.FilterHero)
                },
                onShowFilterDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Show))
                },
            )
            LazyColumn (modifier = Modifier.fillMaxSize()){
                items(state.filteredHeros.size) { heroIndex ->
                    HeroListItem(state.filteredHeros[heroIndex], imageLoader) { heroId ->
                        navigateToDetailsScreen(heroId)
                    }
                }
            }
        }
        if (state.filterDialogState is UIComponentState.Show) {
            HeroListFilter(
                heroFilter = state.heroFilter,
                attribute = state.primaryAttribute,
                onUpdateHeroFilter = { heroFilter ->
                    events(HeroListEvents.UpdateHeroFilter(heroFilter))
                },
                onCloseDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Hide))
                },
                onUpdateAttributeFilter = {heroAttribute ->
                    events(HeroListEvents.UpdateAttributeFilter(heroAttribute))
                }
            )
        }
    }
}