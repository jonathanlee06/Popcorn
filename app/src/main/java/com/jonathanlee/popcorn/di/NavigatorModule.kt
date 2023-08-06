package com.jonathanlee.popcorn.di

import com.jonathanlee.popcorn.util.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NavigatorModule {
    @Singleton
    @Provides
    fun provideNavigator(): Navigator {
        return Navigator.getNavigator()
    }
}