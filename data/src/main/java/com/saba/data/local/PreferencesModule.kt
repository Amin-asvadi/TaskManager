package com.saba.data.local

import android.content.Context
import android.content.SharedPreferences
import com.hamrahdoctor.data_android.preferences.PreferencesStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideCommonPreference(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            PreferencesStorageImpl.PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }
}
