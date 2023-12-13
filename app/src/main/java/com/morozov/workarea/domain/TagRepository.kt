package com.morozov.workarea.domain


import com.apollographql.apollo3.api.ApolloResponse
import com.morozov.AccessToShopWebQuery
import com.morozov.FreeArticleQuery
import com.morozov.GetParselyTopTopicsQuery
import com.morozov.GetReaderPassParselyQuery
import kotlinx.coroutines.flow.Flow


// Repository.kt
interface TagRepository {
    fun getTags(): Flow<ApolloResponse<GetParselyTopTopicsQuery.Data>>
}

interface ArticleRepository {
    fun hasFreeArticleAccess(): Flow<ApolloResponse<FreeArticleQuery.Data>>
}

interface ShopRepository {
    fun hasAccessToShopWeb(): Flow<ApolloResponse<AccessToShopWebQuery.Data>>
}

interface ReaderPassRepository {
    fun getReaderPassPosts(): Flow<ApolloResponse<GetReaderPassParselyQuery.Data>>
}
