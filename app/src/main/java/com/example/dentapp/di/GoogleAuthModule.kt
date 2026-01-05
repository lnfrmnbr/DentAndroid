package com.example.dentapp.di

import android.content.Context
import com.example.dentapp.BuildConfig
import com.example.dentapp.data.oauth.GoogleAuthUiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object GoogleAuthModule {

    @Provides
    @Named("googleClientId")
    fun provideGoogleClientId(): String = BuildConfig.GOOGLE_CLIENT_ID

    @Provides
    fun provideGoogleAuthUiClient(
        @ApplicationContext context: Context,
        @Named("googleClientId") clientId: String
    ): GoogleAuthUiClient = GoogleAuthUiClient(context, clientId)
}