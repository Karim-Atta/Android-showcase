package com.karem.dota.di

import android.app.Application
import com.karem.hero_interactors.HeroInteractors
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroInteractorsModule {

    @Provides
    @Singleton
    @Named("heroAndroidDriver")
    fun provideAndroidDriver(app: Application):SqlDriver{
        return AndroidSqliteDriver(
            schema = HeroInteractors.schema,
            context = app,
            name = HeroInteractors.dbName,
        )
    }

    @Provides
    @Singleton
    fun provideHeroInteractors(@Named("heroAndroidDriver") sqlDriver: SqlDriver): HeroInteractors{
        return HeroInteractors.build(sqlDriver)
    }
}