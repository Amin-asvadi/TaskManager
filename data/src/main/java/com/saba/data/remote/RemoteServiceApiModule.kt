package com.saba.data.remote

import com.saba.data.api.RemoteApi
import com.saba.data.repository.remote.RemoteRepository
import com.saba.data.repositoryimp.remote.RemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
sealed class RemoteServiceApiModule {

    @Binds
    abstract fun provideRemoteServiceRepository(repository: RemoteRepositoryImpl):RemoteRepository

    companion object {
        @Provides
        fun provideRemoteServiceApiService(retrofit: Retrofit): RemoteApi {
            return retrofit.create(RemoteApi::class.java)
        }
    }

}