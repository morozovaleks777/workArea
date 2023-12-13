package com.morozov.workarea.domain


import com.apollographql.apollo3.api.Response
import kotlinx.coroutines.flow.Flow


// Repository.kt
interface TagRepository {
    fun getTags(): Flow<Response<GetTagsQuery.Data>>
}

interface ArticleRepository {
    fun hasFreeArticleAccess(): Flow<Response<HasFreeArticleAccessQuery.Data>>
}

interface ShopRepository {
    fun hasAccessToShopWeb(): Flow<Response<HasAccessToShopWebQuery.Data>>
}

interface ReaderPassRepository {
    fun getReaderPassPosts(): Flow<Response<GetParselyTopPostsQuery.Data>>
}
