package com.furkanfidanoglu.artbooktesting.di

import com.furkanfidanoglu.artbooktesting.repository.ArtRepositoryInterface
import com.furkanfidanoglu.artbooktesting.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(repository: Repository): ArtRepositoryInterface
}