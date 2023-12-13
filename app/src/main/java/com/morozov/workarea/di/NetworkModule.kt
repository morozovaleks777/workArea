package com.morozov.workarea.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

//    // ApolloModule.kt
//    @Module
//    @InstallIn(SingletonComponent::class)
//    object ApolloModule {

        @Provides
        @Singleton
        fun provideApolloClient(): ApolloClient {
            return ApolloClient.builder()
                .serverUrl("https://v2server.dailywire.com/discussion/graphql")
                .build()
        }


    }


//}