package com.morozov.workarea.di


import com.apollographql.apollo3.ApolloClient
import com.morozov.workarea.data.repositoryImpl.ArticleRepositoryImpl
import com.morozov.workarea.data.repositoryImpl.ReaderPassRepositoryImpl
import com.morozov.workarea.data.repositoryImpl.ShopRepositoryImpl
import com.morozov.workarea.data.repositoryImpl.TagRepositoryImpl
import com.morozov.workarea.domain.ArticleRepository
import com.morozov.workarea.domain.ReaderPassRepository
import com.morozov.workarea.domain.ShopRepository
import com.morozov.workarea.domain.TagRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://v2server.dailywire.com/graphql")
            .build()
    }


    @Provides
    @Singleton
    fun provideTagRepository(tagRepositoryImpl: TagRepositoryImpl): TagRepository {
        return tagRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository {
        return articleRepositoryImpl
    }
    @Provides
    @Singleton
    fun provideShopRepository(shopRepositoryImpl: ShopRepositoryImpl): ShopRepository {
        return shopRepositoryImpl
    }
    @Provides
    @Singleton
    fun provideReaderPassRepository(readerPassRepositoryImpl: ReaderPassRepositoryImpl): ReaderPassRepository {
        return readerPassRepositoryImpl
    }

}


