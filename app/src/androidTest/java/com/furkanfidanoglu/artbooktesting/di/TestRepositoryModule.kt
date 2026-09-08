package com.furkanfidanoglu.artbooktesting.di

import com.furkanfidanoglu.artbooktesting.repository.ArtRepositoryInterface
import com.furkanfidanoglu.artbooktesting.repository.FakeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object TestRepositoryModule {

    @Provides
    @Singleton
    fun provideFakeRepository(): ArtRepositoryInterface {
        return FakeRepository()
    }
}