package com.karem.ui_herolist.di

import com.karem.core.Logger
import com.karem.hero_interactors.FilterHeros
import com.karem.hero_interactors.GetHeros
import com.karem.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroListModule {


    @Provides
    @Singleton
    @Named("heroListLogger")
    fun provideLogger(): Logger {
        return Logger(
            tag = "HeroList",
            isDebug = true
        )
    }

    @Provides
    @Singleton
    fun provideGetHeros(
        interactors: HeroInteractors
    ):GetHeros{
        return interactors.getHeros
    }

    @Provides
    @Singleton
    fun providesFilterHeros(
        interactors: HeroInteractors
    ): FilterHeros {
        return interactors.filterHeros
    }
}