package com.example.tubeplay.di

import androidx.lifecycle.ViewModel
import com.example.tubeplay.data.api.YouTubeApiService
import com.example.tubeplay.data.repository.IRepository
import com.example.tubeplay.data.repository.VideoRepositoryImpl
import com.example.tubeplay.presentation.VideoViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideYouTubeApiService(retrofit: Retrofit): YouTubeApiService {
        return retrofit.create(YouTubeApiService::class.java)
    }

}

@Module
@InstallIn(ViewModelComponent::class)
object VMModule {

    @Provides
    @ViewModelScoped
    fun provideVideoRepository(apiService: YouTubeApiService): IRepository {
        return VideoRepositoryImpl(apiService)
    }

    @Provides
    @ViewModelScoped
    fun provideVM(repo: IRepository): ViewModel {
        return VideoViewModel(repo)
    }

}
