package com.morozov.workarea.domain.useCases


import com.apollographql.apollo3.api.ApolloResponse
import com.morozov.AccessToShopWebQuery
import com.morozov.FreeArticleQuery
import com.morozov.GetParselyTopTopicsQuery
import com.morozov.GetReaderPassParselyQuery
import com.morozov.workarea.data.ReaderPassPost
import com.morozov.workarea.domain.ArticleRepository
import com.morozov.workarea.domain.ReaderPassRepository
import com.morozov.workarea.domain.ShopRepository
import com.morozov.workarea.domain.TagRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    fun execute(): Flow<ApolloResponse<GetParselyTopTopicsQuery.Data>> {
        return tagRepository.getTags()
    }
}

class FreeArticleUseCase @Inject constructor(private val freeArticleRepository: ArticleRepository) {
    fun execute(): Flow<ApolloResponse<FreeArticleQuery.Data>> {
        return freeArticleRepository.hasFreeArticleAccess()
    }
}

class AccessToShopWebUseCase @Inject constructor(private val accessToShopWebRepository: ShopRepository) {
    fun execute(): Flow<ApolloResponse<AccessToShopWebQuery.Data>> {
        return accessToShopWebRepository.hasAccessToShopWeb()
    }
}

class ReaderPassParselyUseCase @Inject constructor(private val readerPassParselyRepository: ReaderPassRepository) {
    fun execute(): Flow<ApolloResponse<GetReaderPassParselyQuery.Data>> {
        return readerPassParselyRepository.getReaderPassPosts()
    }
}
