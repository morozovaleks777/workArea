package com.morozov.workarea.data.repositoryImpl

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.morozov.AccessToShopWebQuery
import com.morozov.FreeArticleQuery
import com.morozov.GetParselyTopTopicsQuery
import com.morozov.GetReaderPassParselyQuery
import com.morozov.workarea.domain.ArticleRepository
import com.morozov.workarea.domain.ReaderPassRepository
import com.morozov.workarea.domain.ShopRepository
import com.morozov.workarea.domain.TagRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TagRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : TagRepository {
    override fun getTags(): Flow<ApolloResponse<GetParselyTopTopicsQuery.Data>> {
        return apolloClient.query(GetParselyTopTopicsQuery()).toFlow()
    }
}

class ArticleRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient

) : ArticleRepository {

    override fun hasFreeArticleAccess(): Flow<ApolloResponse<FreeArticleQuery.Data>> {
        return apolloClient.query(FreeArticleQuery()).toFlow()
    }
}

class ShopRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : ShopRepository {
    override fun hasAccessToShopWeb(): Flow<ApolloResponse<AccessToShopWebQuery.Data>> {
        return apolloClient.query(AccessToShopWebQuery()).toFlow()
    }
}

class ReaderPassRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : ReaderPassRepository {

    override fun getReaderPassPosts(): Flow<ApolloResponse<GetReaderPassParselyQuery.Data>> {
        apolloClient.newBuilder().serverUrl("https://v2server.dailywire.com/app/graphql")
        return apolloClient.query(GetReaderPassParselyQuery()).toFlow()
    }
}
