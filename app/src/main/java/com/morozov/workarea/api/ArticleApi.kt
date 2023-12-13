package com.morozov.workarea.api




// GraphQLQueries.kt

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Response
import kotlinx.coroutines.flow.Flow

class GraphQLQueries(
    private val apolloClient: ApolloClient
) {
    suspend fun getTags(): Flow<Response<GetTagsQuery.Data>> {
        return apolloClient.query(GetTagsQuery()).toFlow()
    }

    suspend fun hasFreeArticleAccess(): Flow<Response<HasFreeArticleAccessQuery.Data>> {
        return apolloClient.query(HasFreeArticleAccessQuery()).toFlow()
    }

    suspend fun hasAccessToShopWeb(): Flow<Response<HasAccessToShopWebQuery.Data>> {
        return apolloClient.query(HasAccessToShopWebQuery()).toFlow()
    }

    suspend fun getReaderPassPosts(): Flow<Response<GetParselyTopPostsQuery.Data>> {
        return apolloClient.query(GetParselyTopPostsQuery()).toFlow()
    }
}

