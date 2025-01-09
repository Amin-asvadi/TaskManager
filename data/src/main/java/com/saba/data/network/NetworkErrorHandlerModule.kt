package com.saba.data.network
import com.saba.base_android.network.NetworkErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkErrorHandlerModule {

    @OptIn(DelicateCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideNetworkErrorHandler(): NetworkErrorHandler {
        return NetworkErrorHandlerImpl(GlobalScope)
    }
}
