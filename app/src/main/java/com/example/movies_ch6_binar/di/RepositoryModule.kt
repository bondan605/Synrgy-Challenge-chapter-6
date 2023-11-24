package com.example.movies_ch6_binar.di

import com.example.movies_ch6_binar.data.local.room.dao.FavoriteDao
import com.example.movies_ch6_binar.data.local.room.dao.UserDao
import com.example.movies_ch6_binar.data.remote.dto.TheMovieDbApi
import com.example.movies_ch6_binar.data.repository.AuthRepositoryImpl
import com.example.movies_ch6_binar.data.repository.FavoriteRepositoryImpl
import com.example.movies_ch6_binar.data.repository.MovieRepositoryImpl
import com.example.movies_ch6_binar.Models.repository.AuthRepository
import com.example.movies_ch6_binar.Models.repository.FavoriteRepository
import com.example.movies_ch6_binar.Models.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMovieRepository(api: TheMovieDbApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }

    @Provides
    fun provideAuthRepository(userDao: UserDao): AuthRepository {
        return AuthRepositoryImpl(userDao)
    }

    @Provides
    fun provideFavoriteRepository(favoriteDao: FavoriteDao): FavoriteRepository {
        return FavoriteRepositoryImpl(favoriteDao)
    }
}