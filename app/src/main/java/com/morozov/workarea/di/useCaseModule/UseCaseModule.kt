package com.morozov.workarea.di.useCaseModule

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
class UseCaseModule {

    @Singleton
    @Provides
    fun provideTagRepository(tagRepositoryImpl: TagRepositoryImpl): TagRepository {
        return tagRepositoryImpl
    }

    @Singleton
    @Provides
    fun provideArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository {
        return articleRepositoryImpl
    }

    @Singleton
    @Provides
    fun provideShopRepository(shopRepositoryImpl: ShopRepositoryImpl): ShopRepository {
        return shopRepositoryImpl
    }

    @Singleton
    @Provides
    fun provideReaderPassRepository(readerPassRepositoryImpl: ReaderPassRepositoryImpl): ReaderPassRepository {
        return readerPassRepositoryImpl
    }
}